package sas.misc.test;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import sas.misc.intefaces.NetworkMessage;
import sas.misc.intefaces.NetworkPrivateMessage;

@RunWith(Categories.class)
@IncludeCategory(NetworkMessage.class)
@ExcludeCategory(NetworkPrivateMessage.class)
@SuiteClasses({ ClientPeerTest.class })
public class NetworkMessageTestSuite {

}
