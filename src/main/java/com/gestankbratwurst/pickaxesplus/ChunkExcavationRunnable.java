package com.gestankbratwurst.pickaxesplus;

import java.util.LinkedList;
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
public class ChunkExcavationRunnable implements Runnable {

  public static ChunkExcavationRunnable INSTANCE = new ChunkExcavationRunnable();

  private final LinkedList<ExcavationProcess> processes = new LinkedList<>();

  public void addProcess(final Block block, final ItemStack itemStack) {
    this.processes.add(new ExcavationProcess(block, itemStack == null ? null : itemStack.clone()));
  }

  @Override
  public void run() {
    this.processes.removeIf(ExcavationProcess::proceed);
  }

}
