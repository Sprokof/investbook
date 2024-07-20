/*
 * InvestBook
 * Copyright (C) 2022  Spacious Team <spacious-team@ya.ru>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ru.investbook.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.spacious_team.broker.pojo.Security;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.investbook.converter.SecurityConverter;
import ru.investbook.entity.SecurityEntity;
import ru.investbook.repository.SecurityRepository;

@RestController
@Tag(name = "Инструменты", description = "Акции, облигации, деривативы и валютные пары")
@RequestMapping("/api/v1/securities")
public class SecurityRestController extends AbstractRestController<Integer, Security, SecurityEntity> {
    private final SecurityRepository repository;

    public SecurityRestController(SecurityRepository repository, SecurityConverter converter) {
        super(repository, converter);
        this.repository = repository;
    }

    @Override
    @GetMapping
    @PageableAsQueryParam
    @Operation(summary = "Отобразить все", description = "Отобразить все биржевые инструменты")
    public Page<Security> get(@Parameter(hidden = true)
                              Pageable pageable) {
        return super.get(pageable);
    }


    @Override
    @GetMapping("{id}")
    @Operation(summary = "Отобразить один",
            description = "Отобразить биржевой инструмент по внутреннему идентификатору")
    public ResponseEntity<Security> get(@PathVariable("id")
                                        @Parameter(description = "Идентификатор", example = "123", required = true)
                                        Integer id) {
        return super.get(id);
    }


    @Override
    @PostMapping
    @Operation(summary = "Добавить", description = "Добавить информацию об акции, облигации, деривативе или валютной паре")
    public ResponseEntity<Void> post(@Valid @RequestBody Security security) {
        return super.post(security);
    }

    @Override
    @PutMapping("{id}")
    @Operation(summary = "Обновить", description = "Добавить информацию об акции, облигации, деривативе или валютной паре")
    public ResponseEntity<Void> put(@PathVariable("id")
                                    @Parameter(description = "Идентификатор", example = "123", required = true)
                                    Integer id,
                                    @Valid
                                    @RequestBody
                                    Security security) {
        return super.put(id, security);
    }

    @Override
    @DeleteMapping("{id}")
    @Operation(summary = "Удалить", description = "Удалить сведения о биржевом инструменте и всех его сделках по всем счетам")
    public void delete(@PathVariable("id")
                       @Parameter(description = "Идентификатор", example = "123", required = true)
                       Integer id) {
        super.delete(id);
    }

    @Override
    protected Integer getId(Security object) {
        return object.getId();
    }

    @Override
    protected Security updateId(Integer id, Security object) {
        return object.toBuilder().id(id).build();
    }

    @Override
    protected String getLocation() {
        return "/securities";
    }
}
