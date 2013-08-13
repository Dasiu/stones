package adam.siegestones;

import org.junit.runner.RunWith
import org.junit.runners.Suite
import adam.siegestones.logic.GameControllerTest
import org.junit.runners.Suite.SuiteClasses
import adam.siegestones.logic.LogicTest
import adam.siegestones.models.AIPlayerTest
import adam.siegestones.models.BoardTest

@RunWith(classOf[Suite])
@SuiteClasses(Array(classOf[GameControllerTest], 
							classOf[LogicTest], 
							classOf[AIPlayerTest],
							classOf[BoardTest]))
class AllTests {

}
