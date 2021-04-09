package com.gestankbratwurst.pickaxesplus.excavator;

import java.util.function.Consumer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
public class ExcavatorHandleFour implements Consumer<BlockBreakEvent> {

  @Override
  public void accept(final BlockBreakEvent event) {
    final ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
    final Block block = event.getBlock();
    final Consumer<Block> blockConsumer =
        event.getPlayer().getGameMode() == GameMode.CREATIVE ? bl -> bl.setType(Material.AIR) : bl -> bl.breakNaturally(tool);
    for (int x = -2; x <= 2; x++) {
      for (int y = -2; y <= 2; y++) {
        for (int z = -2; z <= 2; z++) {
          if (x != 0 || y != 0 || z != 0) {
            blockConsumer.accept(block.getRelative(x, y, z));
          }
        }
      }
    }
  }

}
