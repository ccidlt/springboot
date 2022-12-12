package com.ds.utils;

import com.ds.entity.TreeData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {

    /**
     * 构建树节点
     */
    public static <T extends TreeData> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<>();
        //list转map
        Map<Integer, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
        for (T treeNode : treeNodes) {
            nodeMap.put(treeNode.getId(), treeNode);
        }
        for (T node : nodeMap.values()) {
            T parent = nodeMap.get(node.getPid());
            if (parent != null && !(node.getId().equals(parent.getId()))) {
                parent.getChildren().add(node);
                continue;
            }

            result.add(node);
        }
        return result;
    }

    /**
     * 根据pid，构建树节点
     */
    public static <T extends TreeData> List<T> build(List<T> treeNodes, Integer pid) {

        List<T> treeList = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (pid.equals(treeNode.getPid())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }
        return treeList;
    }

    /**
     * 查找子节点
     */
    public static <T extends TreeData> T findChildren(List<T> treeNodes, T rootNode) {
        for (T treeNode : treeNodes) {
            if (rootNode.getId().equals(treeNode.getPid())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 获取子节点
     *
     * @param list
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends TreeData> List<T> recursionDown(List<T> list, Integer id) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (item.getPid() != null && item.getPid().equals(id)) {
                item.setChildren(recursionDown(list, item.getId()));
                result.add(item);
            }
        }
        return result;
    }

    public static <T extends TreeData> void recursionDownIds(List<T> list, Integer id, List<Integer> ids) {
        if (id != null) {
            for (T item : list) {
                if (item.getPid() != null && item.getPid().equals(id)) {
                    ids.add(item.getId());
                    recursionDownIds(list, item.getId(), ids);
                }
            }
        }
    }

    public static <T extends TreeData> void recursionDownNames(List<T> list, Integer id, List<String> names) {
        if (id != null) {
            for (T item : list) {
                if (item.getPid() != null && item.getPid().equals(id)) {
                    names.add(item.getName());
                    recursionDownNames(list, item.getId(), names);
                }
            }
        }
    }

    /**
     * 获取父节点
     *
     * @param list
     * @param pid
     * @param ids
     * @param <T>
     */
    public static <T extends TreeData> void recursionUpIds(List<T> list, Integer pid, List<Integer> ids) {
        if (pid != null) {
            for (T item : list) {
                if (item.getId().equals(pid)) {
                    ids.add(item.getId());
                    recursionUpIds(list, item.getPid(), ids);
                }
            }
        }
    }

    public static <T extends TreeData> void recursionUpNames(List<T> list, Integer pid, List<String> names) {
        if (pid != null) {
            for (T item : list) {
                if (item.getId().equals(pid)) {
                    names.add(item.getName());
                    recursionUpNames(list, item.getPid(), names);
                }
            }
        }
    }

}
