/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.commands;

import java.util.Comparator;
import java.util.stream.StreamSupport;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.wurstclient.BurstFeature;
import net.wurstclient.command.*;
import net.wurstclient.hacks.ProtectHack;
import net.wurstclient.util.FakePlayerEntity;

@BurstFeature(name="protect", description = "Protects the given entity from other entities.",
category = "cmd")
@BurstCmd(syntax = ".protect <entity>")
public final class ProtectCmd extends Command
{

	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length != 1)
			throw new CmdSyntaxError();
		
		ProtectHack protectHack = WURST.getHax().getProtectHack();
		
		if(protectHack.isEnabled())
			protectHack.setEnabled(false);
		
		Entity entity = StreamSupport
			.stream(MC.world.getEntities().spliterator(), true)
			.filter(e -> e instanceof LivingEntity)
			.filter(e -> !e.removed && ((LivingEntity)e).getHealth() > 0)
			.filter(e -> e != MC.player)
			.filter(e -> !(e instanceof FakePlayerEntity))
			.filter(e -> args[0].equalsIgnoreCase(e.getName().getString()))
			.min(
				Comparator.comparingDouble(e -> MC.player.squaredDistanceTo(e)))
			.orElse(null);
		
		if(entity == null)
			throw new CmdError(
				"Entity \"" + args[0] + "\" could not be found.");
		
		protectHack.setFriend(entity);
		protectHack.setEnabled(true);
	}
}
