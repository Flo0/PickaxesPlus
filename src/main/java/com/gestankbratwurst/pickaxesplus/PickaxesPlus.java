package com.gestankbratwurst.pickaxesplus;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public final class PickaxesPlus extends JavaPlugin {

  private static PickaxesPlus instance;

  public static void log(final Object message) {

  }

  private static final LinkedHashMap<String, Integer> ROMAN_NUMERALS = new LinkedHashMap<String, Integer>() {{
    this.put("M", 1000);
    this.put("CM", 900);
    this.put("D", 500);
    this.put("CD", 400);
    this.put("C", 100);
    this.put("XC", 90);
    this.put("L", 50);
    this.put("XL", 40);
    this.put("X", 10);
    this.put("IX", 9);
    this.put("V", 5);
    this.put("IV", 4);
    this.put("I", 1);
  }};

  public static String RomanNumerals(int Int) {
    final StringBuilder res = new StringBuilder();
    for (final Map.Entry<String, Integer> entry : PickaxesPlus.ROMAN_NUMERALS.entrySet()) {
      final int matches = Int / entry.getValue();
      res.append(PickaxesPlus.repeat(entry.getKey(), matches));
      Int = Int % entry.getValue();
    }
    return res.toString();
  }

  private static String repeat(final String s, final int n) {
    if (s == null) {
      return null;
    }
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++) {
      sb.append(s);
    }
    return sb.toString();
  }

  @Override
  public void onEnable() {
    PickaxesPlus.instance = this;
    PickaxesPlus.registerEnchantment(ExcavatorEnchantment.VALUE);
    Bukkit.getPluginManager().registerEvents(new PickAxeListener(), this);
    Bukkit.getScheduler().runTaskTimer(this, ChunkExcavationRunnable.INSTANCE, 1L, 1L);
    Objects.requireNonNull(Bukkit.getPluginCommand("excavation")).setExecutor(new ExcavatorCommand());
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  @SneakyThrows
  private static void registerEnchantment(final Enchantment enchantment) {
    final Field acceptingField = Enchantment.class.getDeclaredField("acceptingNew");
    acceptingField.setAccessible(true);
    acceptingField.setBoolean(null, true);
    Enchantment.registerEnchantment(enchantment);
    Enchantment.stopAcceptingRegistrations();
  }


}
