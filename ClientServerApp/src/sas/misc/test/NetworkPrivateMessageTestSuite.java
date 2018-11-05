package sas.misc.test;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import sas.misc.intefaces.NetworkPrivateMessage;

@RunWith(Categories.class)
@IncludeCategory(NetworkPrivateMessage.class)
@SuiteClasses({ ClientPeerTest.class })
public class NetworkPrivateMessageTestSuite {

}
