package com.emi.order_service.RequestDto;

import java.util.List;

public record RequestOrderDto(
        List<OrderItemRequestDto> items
		) {

}
