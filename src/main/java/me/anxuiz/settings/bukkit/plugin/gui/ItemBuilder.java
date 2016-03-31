package me.anxuiz.settings.bukkit.plugin.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack itemStack;
    private Material material;
    private int amount = 1;
    private short damage = 0;
    private String name;
    private List<String> lore = Lists.newArrayList();
    private Map<Enchantment, Integer> enchantments = Maps.newHashMap();

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setDamage(short damage) {
        this.damage = damage;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder addLore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        for (String string : lore)
            this.lore.add(string);
        return this;
    }

    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int value) {
        this.enchantments.put(enchantment, value);
        return this;
    }

    public ItemStack createItem() {
        if (itemStack == null)
            this.itemStack = new ItemStack(this.material, this.amount, this.damage);
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (this.name != null)
            itemMeta.setDisplayName(this.name);
        if (this.lore != null)
            itemMeta.setLore(this.lore);
        if (this.enchantments != null) {
            for (Enchantment enchantment : this.enchantments.keySet()) {
                itemMeta.addEnchant(enchantment, this.enchantments.get(enchantment), true);
            }
        }
        this.itemStack.setItemMeta(itemMeta);
        return this.itemStack;
    }

}
