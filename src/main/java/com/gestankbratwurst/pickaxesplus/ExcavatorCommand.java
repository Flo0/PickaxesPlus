package com.gestankbratwurst.pickaxesplus;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PickaxesPlus and was created at the 10.04.2021
 *
 * PickaxesPlus can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ExcavatorCommand implements CommandExecutor {

  @Override
  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be executed by a player.");
      return true;
    }
    final Player player = (Player) sender;
    final ItemStack itemStack = player.getInventory().getItemInMainHand();
    if (args.length < 1) {
      return false;
    }
    if (itemStack.getType() == Material.AIR) {
      sender.sendMessage("You need to hold an item in order to enchant something.");
      return true;
    }
    final int lvl;
    try {
      lvl = Integer.parseInt(args[0]);
    } catch (final NumberFormatException e) {
      sender.sendMessage(args[0] + " is not a valid input.");
      return true;
    }
    ExcavatorEnchantment.applyTo(itemStack, lvl);
    sender.sendMessage("Added §eExcavation " + PickaxesPlus.RomanNumerals(lvl) + "§r to your item.");
    return true;
  }
}
