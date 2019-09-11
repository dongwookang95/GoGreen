package server.entity;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class AuditEntityTest {
    @Mock
    AuditEntity auditEntity;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void setCreatedDate() {
        LocalDate testedDate = LocalDate.of(1, 1, 1);
        auditEntity.setCreatedDate(testedDate);
        LocalDate result = auditEntity.getCreatedDate();

        Assert.assertEquals(auditEntity.getCreatedDate(), result);

    }

    @Test
    void setLastModifiedDate() {
        LocalDate testedDate = LocalDate.of(1,1,1);
        auditEntity.setLastModifiedDate(testedDate);
        LocalDate result = auditEntity.getLastModifiedDate();

        Assert.assertEquals(auditEntity.getLastModifiedDate(), result);
    }

    @Test
    void setId() {
        auditEntity.setId(123L);
        Long test = auditEntity.getId();

        Assert.assertEquals(auditEntity.getId(), test);
    }

    @Test
    void getId() {
        Long test = auditEntity.getId();

        Assert.assertEquals(auditEntity.getId(), test);
    }

    @Test
    void getCreatedDate() {
        LocalDate result = auditEntity.getCreatedDate();

        Assert.assertEquals(auditEntity.getCreatedDate(), result);
    }

    @Test
    void getLastModifiedDate() {
        LocalDate result = auditEntity.getLastModifiedDate();

        Assert.assertEquals(auditEntity.getLastModifiedDate(), result);
    }
}
