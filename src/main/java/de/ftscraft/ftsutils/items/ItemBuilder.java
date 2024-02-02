package de.ftscraft.ftsutils.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import de.ftscraft.ftsutils.FTSUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    ItemStack itemStack;
    ItemMeta itemMeta;
    List<Component> lore;

    private static NamespacedKey signKey;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
        this.lore = new ArrayList<>();
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
        this.lore = new ArrayList<>();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
        if(itemMeta.hasLore())
            this.lore = new ArrayList<>(itemMeta.lore());
        else this.lore = new ArrayList<>();
    }

    public ItemBuilder name(String name) {
        itemMeta.displayName(Component.text(name));
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int strength) {
        itemMeta.addEnchant(enchantment, strength, true);
        return this;
    }

    public ItemBuilder shiny() {
        return enchant(Enchantment.DURABILITY, 1);
    }

    public ItemBuilder sign(String text) {
        itemMeta.getPersistentDataContainer().set(signKey, PersistentDataType.STRING, text);
        return this;
    }

    public ItemBuilder lore(String lore) {
        this.lore.add(Component.text(lore));
        return this;
    }

    public ItemBuilder lore(String... lore) {
        for (String s : lore) {
            lore(s);
        }
        return this;
    }

    public ItemBuilder skullTexture(String texture) {
        if (itemMeta instanceof SkullMeta skullMeta) {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            profile.setProperty(new ProfileProperty("textures", texture));
            skullMeta.setPlayerProfile(profile);
            itemMeta = skullMeta;
        }
        return this;
    }

    public ItemBuilder skullOwner(String owner) {
        if (itemMeta instanceof SkullMeta skullMeta) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
            itemMeta = skullMeta;
        }
        return this;
    }

    public ItemBuilder skullOwner(UUID owner) {
        if (itemMeta instanceof SkullMeta skullMeta) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
            itemMeta = skullMeta;
        }
        return this;
    }

    public <T,A> ItemBuilder addPDC(String key, A thing, PersistentDataType<T,A> dataType) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(FTSUtils.getInstance(), key), dataType, thing);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void initSignKey() {
        signKey = new NamespacedKey(FTSUtils.getInstance(), "sign");
    }

    public static NamespacedKey getSignKey() {
        return signKey;
    }

}
