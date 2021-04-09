package com.gestankbratwurst.pickaxesplus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PickaxesPlus and was created at the 09.04.2021
 *
 * PickaxesPlus can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class PickAxeListener implements Listener {

  @EventHandler
  public void onBreak(final BlockBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    final ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
    if (!itemStack.hasItemMeta()) {
      return;
    }
    if (!itemStack.containsEnchantment(ExcavatorEnchantment.VALUE)) {
      return;
    }
    int lvl = itemStack.getEnchantments().get(ExcavatorEnchantment.VALUE);
    if (lvl < 1) {
      lvl = 1;
    } else if (lvl > 5) {
      lvl = 5;
    }
    ExcavatorEnchantment.handle(event, lvl);
  }

}