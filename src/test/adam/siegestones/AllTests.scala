package adam.siegestones;

import org.junit.runner.RunWith
import org.junit.runners.Suite
import adam.siegestones.logic.GameControllerTest
import org.junit.runners.Suite.SuiteClasses
import adam.siegestones.logic.LogicTest
import adam.siegestones.models.BoardTest
import adam.siegestones.models.RandomAIPlayerTest

@RunWith(classOf[Suite])
@SuiteClasses(Array(classOf[GameControllerTest], 
							classOf[LogicTest],
							classOf[BoardTest],
							classOf[RandomAIPlayerTest],
							classOf[LogicTest]))
class AllTests {

}
