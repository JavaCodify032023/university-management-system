package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.uni_struct.Group;
import com.example.universitymanagementsystem.repository.GroupRepository;
import com.example.universitymanagementsystem.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public Long save(Group group) {
        return groupRepository.save(group).getId();
    }
}
