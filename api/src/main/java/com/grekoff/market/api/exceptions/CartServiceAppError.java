package com.grekoff.market.api.exceptions;

public class CartServiceAppError extends AppError {
    public enum CartServiceErrors {
        CART_IS_BROKEN(500),
        CART_ID_GENERATOR_DISABLED(503),
        CART_NOT_FOUND(404);

        private int code;
        CartServiceErrors(int code){
            this.code = code;
        }
        public int getCode(){ return code;}

    }

    public CartServiceAppError(int code, String message) {
        super(code, message);
    }
}
