package com.jim.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.jim.dao.SysDeptMapper;
import com.jim.dto.DeptLevelDto;
import com.jim.model.SysDept;
import com.jim.util.LevelUtil;
import org.springframework.stereotype.Service;

import org.apache.commons.collections.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author 赵建龙
 * @date 2018/5/11
 */
@Service
public class SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    public List<DeptLevelDto> deptTree(){
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList){
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }

        return deptListToTree(dtoList);
    }


    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList){
        if (CollectionUtils.isEmpty(deptLevelList)){
            return Lists.newArrayList();
        }

        // level -> [dept1, dept2, ...]
        // 注意这个高级的数据结构，特别有用 Map<String, List<Object>>
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : deptLevelList){
            levelDeptMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }

        // 按照 seq 从小到大排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });

        // 递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);

        return rootList;
    }

    /**
     * 递归生成树
     * 核心是找到递归的点
     * @param deptLevelList
     * @param level
     * @param levelDeptMap
     */
    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap){
        for (int i = 0; i < deptLevelList.size(); i++){
            // 遍历该层的每个元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);

            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());

            // 处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)){
                // 排序
                Collections.sort(tempDeptList, deptSeqComparator);

                // 设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);

                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }


    public Comparator<DeptLevelDto>  deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
