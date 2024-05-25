package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import com.example.universitymanagementsystem.entity.uni_struct.Department;
import com.example.universitymanagementsystem.entity.uni_struct.Faculty;
import com.example.universitymanagementsystem.entity.uni_struct.Specialty;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.SpecialtyAdmissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SpecialtyAdmissionServiceImplTest {

    @Mock
    private SpecialtyAdmissionRepository specialtyAdmissionRepository;

    @InjectMocks
    private SpecialtyAdmissionServiceImpl specialtyAdmissionService;

    @Test
    void getActiveAdmissions_OK() {
        List<SpecialtyAdmission> specialtyAdmissions = new ArrayList<>();
        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();
        specialtyAdmission.setId(1L);
        specialtyAdmissions.add(specialtyAdmission);

        when(specialtyAdmissionRepository.getAllActive()).thenReturn(specialtyAdmissions);
        List<SpecialtyAdmission> activeAdmissions = specialtyAdmissionService.getActiveAdmissions();
        assertEquals(1, activeAdmissions.size());
        assertEquals(specialtyAdmission.getId(), activeAdmissions.get(0).getId());
    }

    @Test
    void getActiveAdmissions_noActiveAdmissions() {
        when(specialtyAdmissionRepository.getAllActive()).thenReturn(List.of());
        BaseBusinessLogicException exception =
                assertThrows(BaseBusinessLogicException.class, () -> specialtyAdmissionService.getActiveAdmissions());
        assertEquals("Наборы не объявлены",exception.getMessage());
    }

    @Test
    void getActiveAdmissions_itActiveAdmissionsByFacultyId() {
        List<SpecialtyAdmission> specialtyAdmissions = new ArrayList<>();
        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();
        specialtyAdmission.setId(1L);
        specialtyAdmissions.add(specialtyAdmission);

        when(specialtyAdmissionRepository.getAllActive(anyLong())).thenReturn(specialtyAdmissions);
        List<SpecialtyAdmission> activeAdmissions = specialtyAdmissionService.getActiveAdmissions(1L);
        assertEquals(1, activeAdmissions.size());
        assertEquals(specialtyAdmission.getId(), activeAdmissions.get(0).getId());
    }

    @Test
    void getActiveAdmission_noActiveAdmissionsByFacultyId() {
        when(specialtyAdmissionRepository.getAllActive(anyLong())).thenReturn(List.of());
        BaseBusinessLogicException exception =
                assertThrows(BaseBusinessLogicException.class, () -> specialtyAdmissionService.getActiveAdmissions(1L));
        assertEquals("Набор не объявлены",exception.getMessage());
    }


    @Test
    void createNewAdmission_OK() {
        List<SpecialtyAdmission> specAdmissions = new ArrayList<>();

        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();

        Specialty specialty = new Specialty();
        specialty.setId(1L);

        Department department = new Department();
        department.setId(1L);

        Faculty faculty = new Faculty();
        faculty.setId(1L);

        specialtyAdmission.setGroupAmount(3);
        specialtyAdmission.setGroupCapacity(3);
        specialtyAdmission.setCreatedDate(LocalDateTime.of(2024,5,12,12,12));
        specialtyAdmission.setMinScore(120);
        specialtyAdmission.setStartDate(LocalDateTime.of(2024,12,12,12,12));
        specialtyAdmission.setEndDate(LocalDateTime.of(2025,12,12,12,12));
        specialtyAdmission.setSpecialty(specialty);
        specialtyAdmission.setDepartment(department);
        specialtyAdmission.setFaculty(faculty);

        specAdmissions.add(specialtyAdmission);

        when(specialtyAdmissionRepository.getActiveBySpecialtyId(specialty.getId()))
                .thenReturn(new ArrayList<>());
        when(specialtyAdmissionRepository.save(specialtyAdmission)).thenReturn(specialtyAdmission);

        List<SpecialtyAdmission> result = specialtyAdmissionService.createNewAdmission(specialtyAdmission);
        assertEquals(specAdmissions, result);
    }

    @Test
    void createNewAdmission_admissionAlreadyExists() {
        List<SpecialtyAdmission> specialtyAdmissions = new ArrayList<>();
        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();
        Specialty specialty = new Specialty();
        specialty.setId(1L);

        specialtyAdmission.setSpecialty(specialty);
        specialtyAdmissions.add(specialtyAdmission);

        when(specialtyAdmissionRepository.getActiveBySpecialtyId(anyLong())).thenReturn(specialtyAdmissions);
        BaseBusinessLogicException exception =
                assertThrows(BaseBusinessLogicException.class, () -> specialtyAdmissionService.createNewAdmission(specialtyAdmission));
        assertEquals("Уже существует действующий набор на эту специальнсоть",exception.getMessage());
    }

    @Test
    void createNewAdmission_admissionIncorrectGroupAmount() {
        List<SpecialtyAdmission> specAdmissions = new ArrayList<>();

        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();

        Specialty specialty = new Specialty();
        specialty.setId(1L);

        Department department = new Department();
        department.setId(1L);

        Faculty faculty = new Faculty();
        faculty.setId(1L);

        specialtyAdmission.setGroupAmount(0);//IncorrectGroupAmount
        specialtyAdmission.setGroupCapacity(3);
        specialtyAdmission.setCreatedDate(LocalDateTime.of(2024,5,12,12,12));
        specialtyAdmission.setMinScore(120);
        specialtyAdmission.setStartDate(LocalDateTime.of(2024,12,12,12,12));
        specialtyAdmission.setEndDate(LocalDateTime.of(2025,12,12,12,12));
        specialtyAdmission.setSpecialty(specialty);
        specialtyAdmission.setDepartment(department);
        specialtyAdmission.setFaculty(faculty);

        specAdmissions.add(specialtyAdmission);

        when(specialtyAdmissionRepository.getActiveBySpecialtyId(specialty.getId()))
                .thenReturn(new ArrayList<>());

        BaseBusinessLogicException exception =
                assertThrows(BaseBusinessLogicException.class, () -> specialtyAdmissionService.createNewAdmission(specialtyAdmission));
        assertEquals("Некорректный ввод данных",exception.getMessage());

    }

    @Test
    void createNewAdmission_admissionIncorrectGroupCapacity() {
        List<SpecialtyAdmission> specAdmissions = new ArrayList<>();

        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();

        Specialty specialty = new Specialty();
        specialty.setId(1L);

        Department department = new Department();
        department.setId(1L);

        Faculty faculty = new Faculty();
        faculty.setId(1L);

        specialtyAdmission.setGroupAmount(3);
        specialtyAdmission.setGroupCapacity(0);//IncorrectGroupCapacity
        specialtyAdmission.setCreatedDate(LocalDateTime.of(2024,5,12,12,12));
        specialtyAdmission.setMinScore(120);
        specialtyAdmission.setStartDate(LocalDateTime.of(2024,12,12,12,12));
        specialtyAdmission.setEndDate(LocalDateTime.of(2025,12,12,12,12));
        specialtyAdmission.setSpecialty(specialty);
        specialtyAdmission.setDepartment(department);
        specialtyAdmission.setFaculty(faculty);

        specAdmissions.add(specialtyAdmission);

        when(specialtyAdmissionRepository.getActiveBySpecialtyId(specialty.getId()))
                .thenReturn(new ArrayList<>());

        BaseBusinessLogicException exception =
                assertThrows(BaseBusinessLogicException.class, () -> specialtyAdmissionService.createNewAdmission(specialtyAdmission));
        assertEquals("Некорректный ввод данных",exception.getMessage());
    }

    @Test
    void createNewAdmission_admissionIncorrectStartDate() {
        List<SpecialtyAdmission> specAdmissions = new ArrayList<>();

        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();

        Specialty specialty = new Specialty();
        specialty.setId(1L);

        Department department = new Department();
        department.setId(1L);

        Faculty faculty = new Faculty();
        faculty.setId(1L);

        specialtyAdmission.setGroupAmount(3);
        specialtyAdmission.setGroupCapacity(3);
        specialtyAdmission.setCreatedDate(LocalDateTime.of(2024,5,12,12,12));
        specialtyAdmission.setMinScore(120);
        specialtyAdmission.setStartDate(LocalDateTime.of(2024,1,1,1,1));//Дата старта не может быть раньше текущей даты
        specialtyAdmission.setEndDate(LocalDateTime.of(2025,12,12,12,12));
        specialtyAdmission.setSpecialty(specialty);
        specialtyAdmission.setDepartment(department);
        specialtyAdmission.setFaculty(faculty);

        specAdmissions.add(specialtyAdmission);

        when(specialtyAdmissionRepository.getActiveBySpecialtyId(specialty.getId()))
                .thenReturn(new ArrayList<>());

        BaseBusinessLogicException exception =
                assertThrows(BaseBusinessLogicException.class, () -> specialtyAdmissionService.createNewAdmission(specialtyAdmission));
        assertEquals("Некорректный ввод данных",exception.getMessage());
    }

    @Test
    void createNewAdmission_SaveFailed(){
        List<SpecialtyAdmission> specAdmissions = new ArrayList<>();

        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();

        Specialty specialty = new Specialty();
        specialty.setId(1L);

        Department department = new Department();
        department.setId(1L);

        Faculty faculty = new Faculty();
        faculty.setId(1L);

        specialtyAdmission.setGroupAmount(3);
        specialtyAdmission.setGroupCapacity(3);
        specialtyAdmission.setCreatedDate(LocalDateTime.of(2024,5,12,12,12));
        specialtyAdmission.setMinScore(120);
        specialtyAdmission.setStartDate(LocalDateTime.of(2024,12,12,12,12));
        specialtyAdmission.setEndDate(LocalDateTime.of(2025,12,12,12,12));
        specialtyAdmission.setSpecialty(specialty);
        specialtyAdmission.setDepartment(department);
        specialtyAdmission.setFaculty(faculty);

        specAdmissions.add(specialtyAdmission);

        when(specialtyAdmissionRepository.getActiveBySpecialtyId(specialty.getId()))
                .thenReturn(new ArrayList<>());
        when(specialtyAdmissionRepository.save(specialtyAdmission)).thenThrow(BaseBusinessLogicException.class);

        Exception exception =
                assertThrows(Exception.class, () -> specialtyAdmissionService.createNewAdmission(specialtyAdmission));
        assertEquals("Не удалось создать набор",exception.getMessage());

    }
}
