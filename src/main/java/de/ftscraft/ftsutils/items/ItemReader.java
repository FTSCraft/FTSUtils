package de.ftscraft.ftsutils.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemReader {

    public static String getSign(ItemStack itemStack) {
        return getSign(itemStack.getItemMeta());
    }

    public static String getSign(ItemMeta itemMeta) {
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(ItemBuilder.getSignKey(), PersistentDataType.STRING)) {
            return container.get(ItemBuilder.getSignKey(), PersistentDataType.STRING);
        }
        return null;
    }

}
