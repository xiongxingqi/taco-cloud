package com.celestrong.tacocloud.repository.impl;

import com.celestrong.tacocloud.pojo.entity.IngredientRef;
import com.celestrong.tacocloud.pojo.entity.Taco;
import com.celestrong.tacocloud.pojo.entity.TacoOrder;
import com.celestrong.tacocloud.repository.OrderRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public TacoOrder save(TacoOrder tacoOrder) {
        tacoOrder.setPlacedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("insert into taco_order" +
                "(delivery_Name,delivery_Street,delivery_City,delivery_State,delivery_Zip,cc_number,cc_expiration,cc_cvv,placed_at)" +
                "values(?,?,?,?,?,?,?,?,?)", Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
                Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        tacoOrder.getDeliveryName(),
                        tacoOrder.getDeliveryStreet(),
                        tacoOrder.getDeliveryCity(),
                        tacoOrder.getDeliveryState(),
                        tacoOrder.getDeliveryZip(),
                        tacoOrder.getCcNumber(),
                        tacoOrder.getCcExpiration(),
                        tacoOrder.getCcCVV(),
                        tacoOrder.getPlacedAt()
                )
        );
        GeneratedKeyHolder gkh = new GeneratedKeyHolder();
        jdbcOperations.update(psc,gkh);
        long orderId = Objects.requireNonNull(gkh.getKey()).longValue();
        tacoOrder.setId(orderId);
        List<Taco> tacos = tacoOrder.getTacos();
        long orderKey=0L;
        for (Taco taco : tacos) {
            saveTaco(orderId,taco,orderKey++);
        }

        return tacoOrder;
    }

    private Long saveTaco(long orderId, Taco taco,Long orderkey) {
        taco.setCreateAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("insert into taco" +
                "(name,taco_order,taco_order_key,created_at)" +
                "values(?,?,?,?)",
                Types.VARCHAR, Types.BIGINT, Types.BIGINT, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
                taco.getName(),
                orderId,
                orderkey,
                taco.getCreateAt()
        ));
        GeneratedKeyHolder gkh = new GeneratedKeyHolder();
        jdbcOperations.update(psc,gkh);
        long tacoId = Objects.requireNonNull(gkh.getKey()).longValue();
        taco.setId(tacoId);
        List<IngredientRef> ingredients = taco.getIngredients();
        saveIngredientRefs(tacoId,ingredients);
        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredients) {
        long tacoKey=0L;
        for (IngredientRef ingredient : ingredients) {
            jdbcOperations.update("insert into Ingredient_Ref" +
                    "(ingredient,taco,taco_key)" +
                    "values(?,?,?)",ingredient.getIngredient(),tacoId,tacoKey++);
        }

    }

}
