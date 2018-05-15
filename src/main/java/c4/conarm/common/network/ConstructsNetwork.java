/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.network;

import slimeknights.tconstruct.common.TinkerNetwork;

public class ConstructsNetwork {

    public static void init() {
        TinkerNetwork.instance.registerPacket(ArmorStationSelectionPacket.class);
        TinkerNetwork.instance.registerPacket(ArmorStationTextPacket.class);
        TinkerNetwork.instance.registerPacketServer(AccessoryTogglePacket.class);
    }
}