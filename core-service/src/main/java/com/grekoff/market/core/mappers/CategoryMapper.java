package com.grekoff.market.core.mappers;

import com.grekoff.market.api.core.CategoryDto;
import com.grekoff.market.core.entities.Category;
import com.grekoff.market.core.services.CategoryService;

public interface CategoryMapper {

    public CategoryDto mapToDto(Category category);
}
