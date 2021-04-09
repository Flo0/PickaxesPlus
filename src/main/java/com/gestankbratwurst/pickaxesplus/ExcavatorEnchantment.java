package com.gestankbratwurst.pickaxesplus;

import com.gestankbratwurst.pickaxesplus.excavator.ExcavatorHandleFive;
import com.gestankbratwurst.pickaxesplus.excavator.ExcavatorHandleFour;
import com.gestankbratwurst.pickaxesplus.excavator.ExcavatorHandleOne;
import com.gestankbratwurst.pickaxesplus.excavator.ExcavatorHandleThree;
import com.gestankbratwurst.pickaxesplus.excavator.ExcavatorHandleTwo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PickaxesPlus and was created at the 09.04.2021
 *
 * PickaxesPlus can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ExcavatorEnchantment extends Enchantment {

  private static final Map<Integer, Consumer<BlockBreakEvent>> HANDLES = new HashMap<Integer, Consumer<BlockBreakEvent>>() {{
    this.put(1, new ExcavatorHandleOne());
    this.put(2, new ExcavatorHandleTwo());
    this.put(3, new ExcavatorHandleThree());
    this.put(4, new ExcavatorHandleFour());
    this.put(5, new ExcavatorHandleFive());
  }};
  public static final ExcavatorEnchantment VALUE = new ExcavatorEnchantment();

  public static void applyTo(final ItemStack itemStack, final int lvl) {
    if (itemStack == null) {
      return;
    }
    final ItemMeta meta = itemStack.getItemMeta();
    if (meta == null) {
      return;
    }
    if (meta.hasEnchant(ExcavatorEnchantment.VALUE)) {
      meta.removeEnchant(ExcavatorEnchantment.VALUE);
    }
    meta.setLore(ExcavatorEnchantment.applyName(meta.hasLore() ? meta.getLore() : new ArrayList<>(), lvl));
    meta.addEnchant(ExcavatorEnchantment.VALUE, lvl, false);
    itemStack.setItemMeta(meta);
  }

  public static void handle(final BlockBreakEvent event, final int lvl) {
    ExcavatorEnchantment.HANDLES.getOrDefault(lvl, key -> new ExcavatorHandleOne()).accept(event);
  }

  private static List<String> applyName(final List<String> lines, final int lvl) {
    int index = -1;
    for (int i = 0; i < lines.size(); i++) {
      final String line = lines.get(i);
      if (line.startsWith("§7Excavator")) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      lines.set(index, "§7Excavator " + PickaxesPlus.RomanNumerals(lvl));
    } else {
      lines.add("§7Excavator " + PickaxesPlus.RomanNumerals(lvl));
    }
    return lines;
  }

  public ExcavatorEnchantment() {
    super(NamespacedKey.minecraft("excavator_enchantment"));
  }

  @Override
  public String getName() {
    return "Excavator";
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public int getStartLevel() {
    return 1;
  }

  @Override
  public EnchantmentTarget getItemTarget() {
    return EnchantmentTarget.TOOL;
  }

  @Override
  public boolean isTreasure() {
    return false;
  }

  @Override
  public boolean isCursed() {
    return false;
  }

  @Override
  public boolean conflictsWith(final Enchantment other) {
    return false;
  }

  @Override
  public boolean canEnchantItem(final ItemStack item) {
    return item != null && item.getType().toString().contains("PICKAXE");
  }
}
