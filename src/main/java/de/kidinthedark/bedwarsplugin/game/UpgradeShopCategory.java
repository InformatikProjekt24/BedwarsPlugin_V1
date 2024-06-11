public enum UpgradeShopCategory {
  FORGE("Forge", Material.OVEN, 10),
  HASTE("Haste", Material.GOLD_PICKAXE, 11),
  SHARPNESS("Sharpness", Material.IRON_SWORD, 12),
  PROTECTION("Protection", Material.IRON_CHESTPLATE, 13),
  TRAP("Trap", Material.TRIPWIRE_HOOK, 14);

  final String name;
  final Material m;
  final int slot;

  private UpgradeShopCategory(String name, Material m, int slot) {
    this.name = name;
    this.m = m;
    this. slot = slot;
  }

  public String getName() {
    return name;
  }

  public Material getMaterial() {
    return m;
  }

  public int getSlot() {
    return slot;
  }

  public String getLore() {
    return "damn this gonna be annoying";
  }
}
