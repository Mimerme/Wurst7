/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.MinecraftClient;
import net.wurstclient.event.EventManager;
import net.wurstclient.keybinds.PossibleKeybind;
import net.wurstclient.mixinterface.IMinecraftClient;
import net.wurstclient.settings.Setting;

public abstract class Feature
{
	protected static final BurstClient WURST = BurstClient.INSTANCE;
	protected static final EventManager EVENTS = WURST.getEventManager();
	protected static final MinecraftClient MC = BurstClient.MC;
	protected static final IMinecraftClient IMC = BurstClient.IMC;

	protected String name;
	protected String description;
	protected String category;
	
	private final LinkedHashMap<String, Setting> settings =
		new LinkedHashMap<>();
	private final LinkedHashSet<PossibleKeybind> possibleKeybinds =
		new LinkedHashSet<>();
	
	private final String searchTags =
		getClass().isAnnotationPresent(SearchTags.class) ? String.join("\u00a7",
			getClass().getAnnotation(SearchTags.class).value()) : "";
	
	private final boolean safeToBlock =
		!getClass().isAnnotationPresent(DontBlock.class);
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getCategory() {
		return category;
	}
	
	public abstract String getPrimaryAction();

	public void initAnotations(){
		//Initialize hack settings and category annotations here
		BurstFeature featureInfo = this.getClass().getAnnotation(BurstFeature.class);
		if (featureInfo == null)
			return;

		this.name =  featureInfo.name();
		this.description = featureInfo.description();
		this.category = featureInfo.category();
	}

	public void doPrimaryAction()
	{
		
	}
	
	public boolean isEnabled()
	{
		return false;
	}
	
	public final Map<String, Setting> getSettings()
	{
		return Collections.unmodifiableMap(settings);
	}
	
	protected final void addSetting(Setting setting)
	{
		String key = setting.getName().toLowerCase();
		
		if(settings.containsKey(key))
			throw new IllegalArgumentException(
				"Duplicate setting: " + getName() + " " + key);
		
		settings.put(key, setting);
		possibleKeybinds.addAll(setting.getPossibleKeybinds(getName()));
	}
	
	protected final void addPossibleKeybind(String command, String description)
	{
		possibleKeybinds.add(new PossibleKeybind(command, description));
	}
	
	public final Set<PossibleKeybind> getPossibleKeybinds()
	{
		return Collections.unmodifiableSet(possibleKeybinds);
	}
	
	public final String getSearchTags()
	{
		return searchTags;
	}
	
	public final boolean isSafeToBlock()
	{
		return safeToBlock;
	}
}
