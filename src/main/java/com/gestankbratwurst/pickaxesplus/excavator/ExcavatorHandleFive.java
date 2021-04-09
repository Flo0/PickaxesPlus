package com.gestankbratwurst.pickaxesplus.excavator;

import com.gestankbratwurst.pickaxesplus.ChunkExcavationRunnable;
import java.util.function.Consumer;
import org.bukkit.GameMode;
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
public class ExcavatorHandleFive implements Consumer<BlockBreakEvent> {

  @Override
  public void accept(final BlockBreakEvent event) {
    final ItemStack item = event.getPlayer().getGameMode() == GameMode.CREATIVE ? null : event.getPlayer().getInventory().getItemInMainHand();
    ChunkExcavationRunnable.INSTANCE.addProcess(event.getBlock(), item);
  }

}
