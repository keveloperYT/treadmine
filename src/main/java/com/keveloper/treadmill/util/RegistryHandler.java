package com.keveloper.treadmill.util;

import com.keveloper.treadmill.Treadmill;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    public static final DeferredRegister <Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Treadmill.MOD_ID);

    // Items
    // public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", ItemBase::new);

}
