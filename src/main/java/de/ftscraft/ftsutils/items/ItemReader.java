package de.ftscraft.ftsutils.items;

import de.ftscraft.ftsutils.FTSUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemReader {

    public static String getSign(ItemStack itemStack) {
        return getSign(itemStack.getItemMeta());
    }

    public static String getSign(ItemMeta itemMeta) {
        if (itemMeta == null)
            return null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(ItemBuilder.getSignKey(), PersistentDataType.STRING)) {
            return container.get(ItemBuilder.getSignKey(), PersistentDataType.STRING);
        }
        return null;
    }

    public static <T,A> A getPDC(ItemStack itemStack, String key, PersistentDataType<T,A> dataType) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(FTSUtils.getInstance(), key), dataType);
    }

    public static <T,A> void addPDC(ItemStack itemStack, String key, A thing, PersistentDataType<T,A> dataType) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(FTSUtils.getInstance(), key), dataType, thing);
        itemStack.setItemMeta(itemMeta);
    }

}
