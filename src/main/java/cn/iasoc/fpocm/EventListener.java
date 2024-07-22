package cn.iasoc.fpocm;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class EventListener implements Listener {
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        // Get the item that was crafted
        ItemStack item = event.getCurrentItem();
        // Log the item type
        //((Logger) LogManager.getRootLogger()).info("Crafted item: " + item.getType());

        assert item != null;
        if (item.getType().toString().equals("SHIELD")) {
            // Cancel the event
            event.getWhoClicked().sendMessage("§c[FPOCM] 你不能合成盾牌！");
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();

        if (item.getType().toString().equals("SHIELD")) {
            //remove the item from the world
            event.getItem().remove();
            // Cancel the event
            event.setCancelled(true);
        }
        // If item is axe, set damage to 1
        if (item.getType().toString().endsWith("AXE")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if(!meta.toString().contains("attribute-modifiers")){
                //((Logger) LogManager.getRootLogger()).info("Modify item: " + meta);
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"generic.attackDamage", 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                item.setItemMeta(meta);
            }
        }
        // If item is sword, add 310% knock back distance
        if (item.getType().toString().endsWith("SWORD")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if(!meta.toString().contains("attribute-modifiers")) {
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"generic.knockback", 3.1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, modifier);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),"generic.attackDamage", ItemsMeta.get(item.getType().toString()), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                item.setItemMeta(meta);
            }
        }
        // If item is netherite armor, remove knock back resistance
        if (item.getType().toString().contains("NETHERITE")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (isArmor(item) && !meta.hasAttributeModifiers()) {
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", ItemsMeta.get(item.getType().toString()), AttributeModifier.Operation.ADD_NUMBER, GetSlot(item)));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, GetSlot(item)));
                item.setItemMeta(meta);
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        if (item.getType().toString().equals("SHIELD")) {
            event.getPlayer().sendMessage("§c[FPOCM] 你不能使用盾牌！");

            // Check and replace shield in main hand
            ItemStack mainHandItem = event.getPlayer().getInventory().getItemInMainHand();
            if (mainHandItem.getType() == Material.SHIELD) {
                event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }

            // Check and replace shield in offhand
            ItemStack offHandItem = event.getPlayer().getInventory().getItemInOffHand();
            if (offHandItem.getType() == Material.SHIELD) {
                event.getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            }

            // Cancel the event
            event.setCancelled(true);
        }
        // If item is axe, set damage to 1
        if (item.getType().toString().endsWith("AXE")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if(!meta.toString().contains("attribute-modifiers")){
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"generic.attackDamage", 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                item.setItemMeta(meta);
            }
        }
        // If item is sword, add 310% knock back distance
        if (item.getType().toString().endsWith("SWORD")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            //((Logger) LogManager.getRootLogger()).info("Crafted item: " + meta);
            if (!meta.toString().contains("attribute-modifiers")) {
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.knockback", 3.1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, modifier);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", ItemsMeta.get(item.getType().toString()), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                item.setItemMeta(meta);
            }
        }
        // If item is netherite armor, remove knock back resistance
        if (item.getType().toString().contains("NETHERITE")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (isArmor(item) && !meta.hasAttributeModifiers()) {
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", ItemsMeta.get(item.getType().toString()), AttributeModifier.Operation.ADD_NUMBER, GetSlot(item)));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, GetSlot(item)));
                item.setItemMeta(meta);
            }
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        ItemStack item = event.getOldCursor();
        if (item.getType().toString().equals("SHIELD")) {
            // Cancel the event
            event.setCancelled(true);
        }
        // If item is axe, set damage to 1
        if (item.getType().toString().endsWith("AXE")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if(!meta.toString().contains("attribute-modifiers")){
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"generic.attackDamage", 1.0, AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.HAND);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                item.setItemMeta(meta);
            }
        }
        // If item is sword, add 310% knock back distance
        if (item.getType().toString().endsWith("SWORD")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (!meta.toString().contains("attribute-modifiers")) {
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.knockback", 3.1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", ItemsMeta.get(item.getType().toString()), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, modifier);
                item.setItemMeta(meta);
            }
        }
        // If item is netherite armor, remove knock back resistance
        if (item.getType().toString().contains("NETHERITE")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (isArmor(item) && !meta.hasAttributeModifiers()) {
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", ItemsMeta.get(item.getType().toString()), AttributeModifier.Operation.ADD_NUMBER, GetSlot(item)));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, GetSlot(item)));
                item.setItemMeta(meta);
            }
        }
    }
    @EventHandler
    public void onSmitingItem(SmithItemEvent Event){
        ItemStack item = Event.getCurrentItem();
        // If item is netherite armor, remove knock back resistance
        assert item != null;
        if (item.getType().toString().contains("NETHERITE")) {
            // Get the item's meta
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (isArmor(item) && !meta.hasAttributeModifiers()) {
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", ItemsMeta.get(item.getType().toString()), AttributeModifier.Operation.ADD_NUMBER, GetSlot(item)));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, GetSlot(item)));
                item.setItemMeta(meta);
            }
            if (item.getType().toString().endsWith("SWORD") && meta.hasAttributeModifiers()) {
                //clear the attribute modifiers
                meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
                meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", ItemsMeta.get(item.getType().toString()), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, new AttributeModifier(UUID.randomUUID(), "generic.knockback", 3.1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND));
                item.setItemMeta(meta);
            }
        }
    }
    private EquipmentSlot GetSlot(ItemStack item){
        String name = item.getType().toString();
        switch (name.substring(name.lastIndexOf("_") + 1)) {
            case "HELMET":
                return EquipmentSlot.HEAD;
            case "CHESTPLATE":
                return EquipmentSlot.CHEST;
            case "LEGGINGS":
                return EquipmentSlot.LEGS;
            case "BOOTS":
                return EquipmentSlot.FEET;
            default:
                return EquipmentSlot.HAND;
        }
    }
    private static boolean isArmor(ItemStack item) {
        if (item == null || !item.getType().isItem()) {
            return false;
        }

        switch (item.getType()) {
            case NETHERITE_HELMET:
            case NETHERITE_CHESTPLATE:
            case NETHERITE_LEGGINGS:
            case NETHERITE_BOOTS:
                return true;
            default:
                return false;
        }
    }
}
