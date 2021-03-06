/*
 * Copyright (C) 2014 - 2020 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.collect.Multimap;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.wurstclient.BurstClient;
import net.wurstclient.mixinterface.ISwordItem;

@Mixin(SwordItem.class)
public class SwordItemMixin extends ToolItem implements ISwordItem
{
	@Shadow
	@Final
	protected float attackDamage;
	
	@Shadow
	@Final
	private Multimap<EntityAttribute, EntityAttributeModifier> field_23745;
	
	private SwordItemMixin(BurstClient wurst, ToolMaterial material,
                           Settings settings)
	{
		super(material, settings);
	}
	
	@Override
	public float fuckMcAfee()
	{
		return (float)field_23745.get(EntityAttributes.GENERIC_ATTACK_SPEED)
			.iterator().next().getValue();
	}
}
