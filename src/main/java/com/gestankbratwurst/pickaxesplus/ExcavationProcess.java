package com.gestankbratwurst.pickaxesplus;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
public class ExcavationProcess {

  private final Chunk chunk;
  private final Set<Block> checkedBlocks;
  private final ArrayDeque<Block> targetBlocks;
  private final Consumer<Block> blockConsumer;

  public ExcavationProcess(final Block startBlock, final ItemStack tool) {
    this.chunk = startBlock.getChunk();
    final boolean useTool = tool != null;
    this.checkedBlocks = new HashSet<>();
    this.targetBlocks = new ArrayDeque<>();
    this.targetBlocks.add(startBlock);
    this.blockConsumer = useTool ? bl -> bl.breakNaturally(tool) : bl -> bl.setType(Material.AIR);
  }

  public boolean proceed() {
    Block target = this.targetBlocks.poll();
    if (target == null) {
      return true;
    }
    for (int i = 0; i < 27; i++) {
      if (target == null) {
        continue;
      }
      for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
          for (int z = -1; z <= 1; z++) {
            final Block relative = target.getRelative(x, y, z);
            if (relative != target) {
              if (this.isViableBlock(relative)) {
                this.blockConsumer.accept(relative);
                this.targetBlocks.add(relative);
              }
              this.checkedBlocks.add(relative);
            }
          }
        }
      }
      target = this.targetBlocks.poll();
    }
    return false;
  }

  private boolean isViableBlock(final Block block) {
    if (block.getType() == Material.BEDROCK || block.getType() == Material.AIR) {
      return false;
    }
    if (block.getX() >> 4 != this.chunk.getX()) {
      return false;
    }
    if (block.getZ() >> 4 != this.chunk.getZ()) {
      return false;
    }
    return !this.checkedBlocks.contains(block);
  }

}
