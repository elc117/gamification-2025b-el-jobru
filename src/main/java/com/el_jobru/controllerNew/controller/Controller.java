package com.el_jobru.controllerNew.controller;

import com.el_jobru.controllerNew.dto.DTO;
import com.el_jobru.controllerNew.dto.RegisterDiaryDTO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

public interface Controller<T extends DTO, R> {
    Class<T> getDTOClass();

    R process(Context ctx, T t);

    default void register(Context ctx){
        try {
            T dto = ctx.bodyAsClass(getDTOClass());

            R returnDTO = process(ctx, dto);

            ctx.status(200).json(returnDTO);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }

    default void update(Context ctx){}

    default void delete(Context ctx){}

    default void getAll(Context ctx){}

}
