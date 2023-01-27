package com.controller;



import com.model.dto.BaseDTO;
import com.model.dto.Response;
import com.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public abstract class BaseController<S extends IBaseService<?, D, ?, ?>, D extends BaseDTO> {

    @Autowired
    protected S service;

    @GetMapping(value = {""})
    public Response<List<D>> getAll(@RequestParam Map<String, String> params,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @Min(value = 0)
                              @RequestParam(value = "size", defaultValue = "50") Integer size) {
        Page<D> dtos = service.getAll(params,page - 1, size);
        return Response.ofSucceeded(dtos);
    }

    @GetMapping(value = {"/{id}"})
    public Response<D> getDetails(@PathVariable("id") Integer id) {
        Optional<D> dto = service.getDetails(id);
        if (dto.isPresent()) {
            return Response.ofSucceeded(dto.get());
        }
        return Response.ofSucceeded();
    }

    @PostMapping(value = {""})
    @Validated(BaseDTO.Create.class)
    public Response<D> create(@RequestBody @Valid D dto) {
        return Response.ofSucceeded(service.create(dto));
//        return Response.status(HttpStatus.CREATED).body(service.create(dto));
    }

    //    @JsonView(Views.External.class)
    @Validated(BaseDTO.Update.class)
    @PutMapping(value = {"/{id}"})
    public Response<D> update(@RequestBody @Valid D dto,
                              @PathVariable @Positive Integer id) {
        dto.setId(id);
        return Response.ofSucceeded(service.update(dto));
    }

    @DeleteMapping(value = {"/{id}"})
    public Response<Void> delete(@PathVariable @Positive Integer id) {
        service.delete(id);
        return Response.ofSucceeded();
    }

    @DeleteMapping(value = {""})
    public Response<Void> deleteMany(@PathVariable List<@Positive Integer> ids) {
        service.delete(ids);
        return Response.ofSucceeded();
    }

    protected abstract List<String> getAllowedProperties();
}