package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.ProgramRequest;
import com.Backend.dto.ProgramResponse;
import com.Backend.entity.Program;
import com.Backend.exception.ProgramAlreadyExistsException;
import com.Backend.exception.ProgramNotFoundException;
import com.Backend.repository.ProgramRepository;
import com.Backend.repository.ProgramResourceRepository;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramResourceRepository programResourceRepository;

    public ProgramService(
            ProgramRepository programRepository, ProgramResourceRepository programResourceRepository) {

        this.programRepository = programRepository;
		this.programResourceRepository = programResourceRepository;
    }

    public ApiResponse<ProgramResponse> createProgram(
            ProgramRequest request) {

        if (programRepository.existsByTitleIgnoreCase(
                request.getTitle())) {

            throw new ProgramAlreadyExistsException(
                    "Program already exists");
        }

        Program program = new Program();

        program.setTitle(request.getTitle());
        program.setDescription(request.getDescription());
        program.setLink(request.getLink());

        Program savedProgram =
                programRepository.save(program);

        return new ApiResponse<>(
                true,
                "Program created successfully",
                mapToResponse(savedProgram));
    }

    public ApiResponse<List<ProgramResponse>>
    getAllPrograms() {

        List<ProgramResponse> programs =
                programRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Programs fetched successfully",
                programs);
    }

    public ApiResponse<ProgramResponse>
    getProgramById(Long id) {

        Program program =
                programRepository.findById(id)
                        .orElseThrow(() ->
                                new ProgramNotFoundException(
                                        "Program not found"));

        return new ApiResponse<>(
                true,
                "Program fetched successfully",
                mapToResponse(program));
    }

    public ApiResponse<ProgramResponse>
    updateProgram(
            Long id,
            ProgramRequest request) {

        Program program =
                programRepository.findById(id)
                        .orElseThrow(() ->
                                new ProgramNotFoundException(
                                        "Program not found"));

        program.setTitle(request.getTitle());
        program.setDescription(request.getDescription());
        program.setLink(request.getLink());

        Program updatedProgram =
                programRepository.save(program);

        return new ApiResponse<>(
                true,
                "Program updated successfully",
                mapToResponse(updatedProgram));
    }

    @Transactional
    public ApiResponse<Object>
    deleteProgram(Long id) {

        Program program =
                programRepository.findById(id)
                        .orElseThrow(() ->
                                new ProgramNotFoundException(
                                        "Program not found"));
        
        programResourceRepository.deleteAllByProgramId(id);
        programRepository.delete(program);

        return new ApiResponse<>(
                true,
                "Program deleted successfully",
                null);
    }

    private ProgramResponse mapToResponse(
            Program program) {

        ProgramResponse response =
                new ProgramResponse();

        response.setId(program.getId());
        response.setTitle(program.getTitle());
        response.setDescription(program.getDescription());
        response.setLink(program.getLink());

        return response;
    }
}
