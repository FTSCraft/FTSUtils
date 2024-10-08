package de.ftscraft.ftsutils.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import de.ftscraft.ftsutils.FTSUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    final ItemStack itemStack;
    ItemMeta itemMeta;
    final List<Component> lore;

    private static NamespacedKey signKey = null;

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
        itemMeta.displayName(LegacyComponentSerializer.legacySection().deserialize(name).decoration(TextDecoration.ITALIC, false));
        return this;
    }

    public ItemBuilder name(Component name) {
        itemMeta.displayName(name);
        return this;
    }

    public ItemBuilder color(Color color) {
        if (itemMeta instanceof ColorableArmorMeta colorableArmorMeta) {
            colorableArmorMeta.setColor(color);
        }
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int strength) {
        itemMeta.addEnchant(enchantment, strength, true);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags){
        itemStack.addItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        itemStack.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder shiny() {
        return enchant(Enchantment.DURABILITY, 1);
    }

    public ItemBuilder sign(String text) {
        if (getSignKey() == null)
            initSignKey();
        itemMeta.getPersistentDataContainer().set(getSignKey(), PersistentDataType.STRING, text);
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
        itemMeta.lore(lore);
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
