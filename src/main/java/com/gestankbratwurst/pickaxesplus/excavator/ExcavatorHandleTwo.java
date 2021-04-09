package com.gestankbratwurst.pickaxesplus.excavator;

import java.util.EnumMap;
import java.util.Map;
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
public class ExcavatorHandleTwo implements Consumer<BlockBreakEvent> {

  private final Map<BlockFace, int[][]> relativeFaces = new EnumMap<BlockFace, int[][]>(BlockFace.class) {{
    this.put(BlockFace.SOUTH, new int[][]{{1, 0, 0}, {1, 1, 0}, {0, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {0, -1, 0}, {-1, 0, 0}, {-1, -1, 0}});
    this.put(BlockFace.NORTH, new int[][]{{1, 0, 0}, {1, 1, 0}, {0, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {0, -1, 0}, {-1, 0, 0}, {-1, -1, 0}});
    this.put(BlockFace.EAST, new int[][]{{0, 1, 1}, {0, 1, 0}, {0, 0, 1}, {0, -1, -1}, {0, -1, 0}, {0, 0, -1}, {0, 1, -1}, {0, -1, 1}});
    this.put(BlockFace.WEST, new int[][]{{0, 1, 1}, {0, 1, 0}, {0, 0, 1}, {0, -1, -1}, {0, -1, 0}, {0, 0, -1}, {0, 1, -1}, {0, -1, 1}});
    this.put(BlockFace.UP, new int[][]{{1, 0, 1}, {1, 0, 0}, {0, 0, 1}, {-1, 0, -1}, {-1, 0, 0}, {0, 0, -1}, {-1, 0, 1}, {1, 0, -1}});
    this.put(BlockFace.DOWN, new int[][]{{1, 0, 1}, {1, 0, 0}, {0, 0, 1}, {-1, 0, -1}, {-1, 0, 0}, {0, 0, -1}, {-1, 0, 1}, {1, 0, -1}});
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
    for (final int[] relPos : this.relativeFaces.get(face)) {
      blockConsumer.accept(block.getRelative(relPos[0], relPos[1], relPos[2]));
    }
  }

}
