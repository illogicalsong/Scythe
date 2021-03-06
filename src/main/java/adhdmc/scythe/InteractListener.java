package adhdmc.scythe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Cocoa;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class InteractListener implements Listener {

    List<Material> farmables = Arrays.asList(Material.CARROTS, Material.COCOA, Material.NETHER_WART, Material.POTATOES, Material.WHEAT, Material.BEETROOTS);

    @EventHandler
    public void rightClickFarmable(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }
        if (event.getHand() == null || event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        if (!(farmables.contains(event.getClickedBlock().getType()))) {
            return;
        }
        Player player = event.getPlayer();
        Block clickedSpot = event.getClickedBlock();
        Material clickedMaterial = clickedSpot.getType();
        Ageable clickedCrop = (Ageable) clickedSpot.getBlockData();
        ItemStack itemUsed = player.getInventory().getItemInMainHand();
        String itemUsedName = itemUsed.toString();
        BlockFace facing = null;
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        if (clickedCrop.getMaximumAge() != clickedCrop.getAge()) {
            return;
        }
        if (!player.hasPermission("scythe.use")){
            return;
        }
        if (playerPDC.has(new NamespacedKey(Scythe.plugin, "toggle"), PersistentDataType.STRING) && playerPDC.get(new NamespacedKey(Scythe.plugin, "toggle"), PersistentDataType.STRING).equals("false")){
            return;
        }
        if (Scythe.plugin.getConfig().getBoolean("Require Hoe") && !itemUsedName.contains("_HOE")){
            return;
        }
        if (clickedMaterial.equals(Material.COCOA)){
            Cocoa clickedCocoa = (Cocoa) event.getClickedBlock().getBlockData();
            facing = clickedCocoa.getFacing();
            Cocoa cocoaData = (Cocoa) Bukkit.createBlockData(Material.COCOA);
            event.setCancelled(true);
            event.getClickedBlock().breakNaturally(itemUsed);
            cocoaData.setFacing(facing);
            clickedSpot.setBlockData(cocoaData);
            return;
            }
        event.setCancelled(true);
        event.getClickedBlock().breakNaturally(itemUsed);
        event.getClickedBlock().setType(clickedMaterial);
    }
}



