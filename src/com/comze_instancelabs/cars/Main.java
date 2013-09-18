package com.comze_instancelabs.cars;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class Main extends JavaPlugin implements Listener{
	
	//TODO: Features
	// [HIGH] not smooth enough
	
	//TODO: BUGS
	// -/-
	
	@Override
    public void onEnable(){
		getLogger().info("onEnable has been invoked!");
		getServer().getPluginManager().registerEvents(this, this);
    }
 
    @Override
    public void onDisable() {
    	getLogger().info("onDisable has been invoked!");
    }
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		return false;
    }
    
    
    //spawning a car:
    
    @EventHandler
	void interact(PlayerInteractEvent event) {
    	if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
    	Block block = event.getClickedBlock();
    	if (event.getPlayer().getItemInHand().getTypeId() == 328) {
    		Location loc = block.getLocation().add(0, 1, 0);
			loc.setYaw(event.getPlayer().getLocation().getYaw() + 270);
			event.getPlayer().getWorld().spawnEntity(loc, EntityType.MINECART);
    	}
    	if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.getPlayer().getInventory().removeItem(new ItemStack(328));
		}
    }
    
	/*@EventHandler
    public void onVehicleEnter(VehicleEnterEvent event){
    	Minecart b = (Minecart)event.getVehicle();
    	b.setMaxSpeed(10D);
    	b.setDerailedVelocityMod(new Vector(10, 10, 10));
    	b.setVelocity(new Vector(10, 10, 10));
    }*/
	
	@EventHandler
	public void onVehicleUpdate(VehicleUpdateEvent event) {
		Vehicle vehicle = event.getVehicle();
		Entity passenger = vehicle.getPassenger();
		if (!(passenger instanceof Player)) {
			return;
		}
		Player player = (Player) passenger;
		if (vehicle instanceof Minecart) {
			Minecart car = (Minecart) vehicle;

			Location under = vehicle.getLocation();
			under.setY(vehicle.getLocation().getY() - 1);
			Block underblock = under.getBlock();
			Block underunderblock = underblock.getRelative(BlockFace.DOWN);
			Block normalblock = vehicle.getLocation().getBlock();
			Block up = normalblock.getLocation().add(0, 1, 0).getBlock();
			
			Location loc = car.getLocation();
			Vector playerVelocity = car.getPassenger().getVelocity();
			double multiplier = 40;
			double maxSpeed = 50;
			car.setMaxSpeed(maxSpeed);
			Vector Velocity = playerVelocity.multiply(multiplier);
			
			float dir = (float) player.getLocation().getYaw();
			car.getLocation().setYaw(dir);
			//car.setVelocity(Velocity);
			
			player.playEffect(player.getLocation(), Effect.SMOKE, 1);
			
			//Location before = car.getLocation();
			Location before = car.getLocation();
			BlockFace faceDir = ClosestFace.getClosestFace(dir);
			int modX = faceDir.getModX() * 1;
			int modY = faceDir.getModY() * 1;
			int modZ = faceDir.getModZ() * 1;
			before.add(modX, modY, modZ);
			Block block = before.getBlock();
			//Block block = underblock;
			//getServer().broadcastMessage(block.getType().name().toString());
			double y = 0;
			if (block.getType() == Material.STEP
					|| block.getType() == Material.DOUBLE_STEP) {
				y = 5;
			}
			Material carBlock = car.getLocation().getBlock().getType();
			if (carBlock == Material.WOOD_STAIRS
					|| carBlock == Material.COBBLESTONE_STAIRS
					|| carBlock == Material.BRICK_STAIRS
					|| carBlock == Material.SMOOTH_STAIRS
					|| carBlock == Material.NETHER_BRICK_STAIRS
					|| carBlock == Material.SANDSTONE_STAIRS
					|| carBlock == Material.SPRUCE_WOOD_STAIRS
					|| carBlock == Material.BIRCH_WOOD_STAIRS
					|| carBlock == Material.JUNGLE_WOOD_STAIRS
					|| carBlock == Material.QUARTZ_STAIRS) {
				y = 5;
			}
			Boolean ignore = false;
			if (car.getVelocity().getY() > 0) {
				ignore = true;
			}
			if (!ignore) {
				Velocity.setY(y);
			}
			car.setVelocity(Velocity);
			
			/*if (normalblock.getTypeId() != 0 && normalblock.getTypeId() != 8
					&& normalblock.getTypeId() != 9
					&& normalblock.getTypeId() != 44
					&& normalblock.getTypeId() != 43
					&& normalblock.getTypeId() != 70
					&& normalblock.getTypeId() != 72
					&& normalblock.getTypeId() != 31) {
				car.setVelocity(new Vector(0, 1, 0));
				// player.getWorld().createExplosion(loc, 0);
			}
			if (up.getTypeId() != 0 && up.getTypeId() != 8
					&& up.getTypeId() != 9 && up.getTypeId() != 44
					&& up.getTypeId() != 43) {
				car.setVelocity(new Vector(0, 1, 0));
				// player.getWorld().createExplosion(loc, 0);
			}
			if (playerVelocity.getX() == 0 && playerVelocity.getZ() == 0) {
				return;
			}
			if (Velocity.getY() < 0) {
				double newy = Velocity.getY() + 1d;
				Velocity.setY(newy);
			}
			car.setMaxSpeed(maxSpeed);
			Location before = car.getLocation();
			float dir = (float) player.getLocation().getYaw();

			car.getLocation().setYaw(dir);
			car.setVelocity(Velocity);*/
		}
	}
}
