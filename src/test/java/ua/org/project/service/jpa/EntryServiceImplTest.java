package ua.org.project.service.jpa;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ua.org.project.domain.impl.Entry;
import ua.org.project.service.EntryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/jpa-app-context-test.xml"})
public class EntryServiceImplTest {

	@Autowired
	private EntryService entryService;
	
	@Test
	public void testInsert() {
		Entry entry = new Entry();
		entry.setSubject("Testing entry clarence");
		entry.setBody("Testing entry clarence");
		entry.setPostDate(new DateTime());
		entry.setCreatedBy("prospring3");
		entry.setCreatedDate(new DateTime());
		entry.setLastModifiedBy("prospring3");
		entry.setLastModifiedDate(new DateTime());		
		entryService.save(entry);
		System.out.println("Entry: " + entry);		
	}
	
}
