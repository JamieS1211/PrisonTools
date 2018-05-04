package com.github.jamies1211.prisontools.ActionsConfig;

import ninja.leaping.configurate.ConfigurationNode;

/**
 * Created by Spark on 25/9/2016
 */
public class ConfigDataInteraction {

    public static Boolean getNodeExists(String NodeBoolToGet) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(NodeBoolToGet).getValue() != null;
    }

    public static Boolean getNodeBool(String NodeBoolToGet) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(NodeBoolToGet).getBoolean();
    }


    public static int getNodeMapSize(String node,String type) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(node, type).getChildrenMap().keySet().size();
    }

    //get node using key
    public static ConfigurationNode getConfigNode(String Node, String type, java.lang.Object key) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(Node, type, key);
    }
    //get node using string
    public static ConfigurationNode getConfigNode(String Node, String type, String key) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(Node, type, key);
    }

    public static boolean getSubNodeBoundsExists(String Bounds, String eventType, String stage, String pos, String axis) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(Bounds, eventType, stage, pos, axis).getValue() != null;
    }
    public static int getPrisonConfigInt(String parentNode, String subNode, String valueNode) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(parentNode, subNode, valueNode).getInt();
    }

    public static double getPrisonConfigDouble(String parentNode, String subNode, String valueNode) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(parentNode, subNode, valueNode).getDouble();
    }

    public static int getSubNodeBounds(String Bounds, String eventType, String stage, String posNode, String axis) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(Bounds, eventType, stage, posNode, axis).getInt();
    }

    public static int getEventBounds(String Bounds, String Area, String posNode, String axis) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(Bounds, Area, posNode, axis).getInt();
    }

    public static void setBounds(String Bounds, String eventType, String stage, String posNode, String axis,String valueToSet) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        generalDataConfig.getNode(Bounds, eventType, stage, posNode, axis).setValue(valueToSet);
    }

    public static boolean getSpawnExists(String ParentNode, String eventType, String stage, String posNode) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(ParentNode, eventType, stage, posNode).getValue() != null;
    }


    public static double getEventSpawnDouble(String ParentNode, String eventType, String stage, String varToGet) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(ParentNode, eventType, stage, varToGet).getDouble();
    }

    public static String getEventSpawnFacing(String ParentNode, String eventType, String stage, String varToGet) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(ParentNode, eventType, stage, varToGet).getString();
    }

    public static void setEventSpawnValue(String ParentNode, String eventType, String stage, String posNode, String valueToSet) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        generalDataConfig.getNode(ParentNode, eventType, stage, posNode).setValue(valueToSet);
    }


    public static double getSpawnDouble(String ParentNode, String posNode) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(ParentNode, posNode).getDouble();
    }

    public static String getSpawnFacing(String ParentNode, String posNode) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(ParentNode, posNode).getString();
    }


    //Gyms, Safari Etc
    public static double getEventSpawnDouble(String ParentNode, String gymRank, String posNode) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(ParentNode,gymRank, posNode).getDouble();
    }

    //Gyms, Safari Etc
    public static String getEventSpawnFacing(String ParentNode, String gymRank, String posNode) {
        ConfigurationNode generalDataConfig = GeneralDataConfig.getConfig().get();
        return generalDataConfig.getNode(ParentNode,gymRank, posNode).getString();
    }
}
