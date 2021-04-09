package com.gestankbratwurst.pickaxesplus.excavator;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PickaxesPlus and was created at the 09.04.2021
 *
 * PickaxesPlus can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ExcavatorHandleOne implements Consumer<BlockBreakEvent> {

  private final Map<BlockFace, Set<BlockFace>> relativeFaces = new EnumMap<BlockFace, Set<BlockFace>>(BlockFace.class) {{
    this.put(BlockFace.UP, EnumSet.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST));
    this.put(BlockFace.DOWN, EnumSet.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST));
    this.put(BlockFace.EAST, EnumSet.of(BlockFace.UP, BlockFace.DOWN, BlockFace.SOUTH, BlockFace.NORTH));
    this.put(BlockFace.WEST, EnumSet.of(BlockFace.UP, BlockFace.DOWN, BlockFace.SOUTH, BlockFace.NORTH));
    this.put(BlockFace.NORTH, EnumSet.of(BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST));
    this.put(BlockFace.SOUTH, EnumSet.of(BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST));
  }};

  @Override
  public void accept(final BlockBreakEvent event) {
    final ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
    final RayTraceResult result = event.getPlayer().rayTraceBlocks(5);
    if (result == null || result.getHitBlockFace() == null) {
      return;
    }
    final BlockFace face = result.getHitBlockFace();
    final Block block = event.getBlock();
    final Consumer<Block> blockConsumer =
        event.getPlayer().getGameMode() == GameMode.CREATIVE ? bl -> bl.setType(Material.AIR) : bl -> bl.breakNaturally(tool);
    for (final BlockFace relative : this.relativeFaces.get(face)) {
      blockConsumer.accept(block.getRelative(relative));
    }
  }

}
