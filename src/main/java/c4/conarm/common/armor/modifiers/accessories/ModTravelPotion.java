/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.common.armor.modifiers.accessories;

import c4.conarm.ConstructsArmory;
import c4.conarm.client.models.accessories.ModelBelt;
import c4.conarm.client.utils.GuiHandler;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.modifiers.IAccessoryRender;
import c4.conarm.lib.utils.ConstructUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;

public class ModTravelPotion extends AccessoryModifier implements IAccessoryRender {

    private static final String TAG_POTION = "potions";

    @SideOnly(Side.CLIENT)
    private static ModelBelt model;
    private static ResourceLocation texture = ConstructUtils.getResource("textures/models/accessories/travel_belt.png");

    public ModTravelPotion() {
        super("potion_belt");
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        player.openGui(ConstructsArmory.instance, GuiHandler.GUI_POTIONBELT_ID, player.world, 0, 0, 0);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        super.applyEffect(rootCompound, modifierTag);
        NBTTagCompound oldBelt = modifierTag.getCompoundTag(TAG_POTION);
        if (oldBelt.isEmpty()) {
            modifierTag.setTag(TAG_POTION, (new ItemStackHandler(7)).serializeNBT());
        } else {
            modifierTag.setTag(TAG_POTION, oldBelt);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onAccessoryRender(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (model == null) {
            model = new ModelBelt();
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        model.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.LEGS && super.canApplyCustom(stack);
    }

    public static class PotionsData extends ModifierNBT {

        public ItemStackHandler potions = new ItemStackHandler(7);

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            potions.deserializeNBT(tag.getCompoundTag(TAG_POTION));
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setTag(TAG_POTION, potions.serializeNBT());
        }
    }
}
