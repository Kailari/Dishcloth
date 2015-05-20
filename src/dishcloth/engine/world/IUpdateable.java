package dishcloth.engine.world;

/**
 * ********************************************************************************************************************
 * IUpdateable.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 14.5.2015
 */

public interface IUpdateable {

    void DoUpdate(float delta);

    void Update(float delta);

    void DoFixedUpdate(float fixedDelta);

    void FixedUpdate(float fixedDelta);
}
