package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RootPMTest {
    @BeforeEach
    void setUp() {
        sut = new RootPM();
    }
    private RootPM sut;

    @Test
    void testGetWasserkraftwerke(){

        // given
        ObservableList<PowerplantsPM> wasserkraftwerke = sut.getAllPowerplants(); // sut = subject under test

        //when
        assertTrue(wasserkraftwerke.size() > 1);

        //then
        assertEquals("Val Giuf", wasserkraftwerke.get(0).getName());
        assertEquals("Chasseras", wasserkraftwerke.get(wasserkraftwerke.size()-1).getName());
    }

    @Test
    void testSave(){
        //given

        //when
        sut.getAllPowerplants().get(0).setName("SavedPPLT");
        sut.save();

        RootPM secondPM = new RootPM();

        //then
        assertEquals(sut.getAllPowerplants().size(), secondPM.getAllPowerplants().size());
        assertEquals("SavedPPLT", secondPM.getAllPowerplants().get(0).getName());

        for (int i = 0; i < sut.getAllPowerplants().size(); i++) {
            assertEquals(sut.getAllPowerplants().get(i).getName(), secondPM.getAllPowerplants().get(i).getName());
        }

        // after
        sut.getAllPowerplants().get(0).setName("Val Giuf");
        sut.save();

    }


}