package backend.entity.user;

import backend.entity.Achievement;
import backend.entity.habit.energy.LowerTemperature;
import backend.entity.habit.energy.SolarPanel;
import backend.entity.habit.food.LocalProduce;
import backend.entity.habit.food.VegetarianMeal;
import backend.entity.habit.structure.OverviewElement;
import backend.entity.habit.transport.PublicTransport;
import backend.entity.habit.transport.TravelByBike;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FriendTest {

    private Friend test;

    @BeforeEach
    void setUp() {
        test = new Friend();
    }

    @Test
    void getVegetarianMeals() {
        assertEquals(new ArrayList<VegetarianMeal>(), test.getVegetarianMeals());
    }

    @Test
    void getLocalProduces() {
        assertEquals(new ArrayList<LocalProduce>(), test.getLocalProduces());
    }

    @Test
    void getSolarPanels() {
        assertEquals(new ArrayList<SolarPanel>(), test.getSolarPanels());
    }

    @Test
    void getLowerTemperatures() {
        assertEquals(new ArrayList<LowerTemperature>(), test.getLowerTemperatures());
    }

    @Test
    void getPublicTransports() {
        assertEquals(new ArrayList<PublicTransport>(), test.getPublicTransports());
    }

    @Test
    void getTravelByBikes() {
        assertEquals(new ArrayList<TravelByBike>(), test.getTravelByBikes());
    }

    @Test
    void setVegetarianMeals() {
        ArrayList<VegetarianMeal> list = new ArrayList<>();
        list.add(new VegetarianMeal());
        test.setVegetarianMeals(list);
        assertEquals(list, test.getVegetarianMeals());
    }

    @Test
    void setLocalProduces() {
        ArrayList<LocalProduce> list = new ArrayList<>();
        list.add(new LocalProduce());
        test.setLocalProduces(list);
        assertEquals(list, test.getLocalProduces());
    }

    @Test
    void setSolarPanels() {
        ArrayList<SolarPanel> list = new ArrayList<>();
        list.add(new SolarPanel());
        test.setSolarPanels(list);
        assertEquals(list, test.getSolarPanels());
    }

    @Test
    void setLowerTemperatures() {
        ArrayList<LowerTemperature> list = new ArrayList<>();
        list.add(new LowerTemperature());
        test.setLowerTemperatures(list);
        assertEquals(list, test.getLowerTemperatures());
    }

    @Test
    void setPublicTransports() {
        Friend test = new Friend("Andy");
        ArrayList<PublicTransport> list = new ArrayList<>();
        list.add(new PublicTransport());
        test.setPublicTransports(list);
        assertEquals(list, test.getPublicTransports());
    }

    @Test
    void setTravelByBikes() {
        ArrayList<TravelByBike> list = new ArrayList<>();
        list.add(new TravelByBike());
        test.setTravelByBikes(list);
        assertEquals(list, test.getTravelByBikes());
    }

    @Test
    void getAchievements() {
        assertEquals(new ArrayList<Achievement>(), test.getAchievements());
    }

    @Test
    void equalsSuccess_diffEmpty() {
        Friend test1 = new Friend();
        Friend test2 = new Friend();
        assertEquals(test1, test2);
    }

    @Test
    void equalsSuccess_diffJustUsr() {
        Friend test1 = new Friend("andy");
        Friend test2 = new Friend("andy");
        assertEquals(test1, test2);
        assertEquals(test1, test1);
    }

    @Test
    void equalsNullUsernames() {
        Friend t1 = new Friend();
        Friend t2 = new Friend("andy");
        assertNotEquals(t2, t1);
    }

    @Test
    void equalsFail_NotClass() {
        LocalProduce lp = new LocalProduce();
        assertNotEquals(test, lp);
    }

    @Test
    void equalsFail_DiffName() {
        Friend f1 = new Friend("andy");
        Friend f2 = new Friend("jan");
        assertNotEquals(f1, f2);
    }

    @Test
    void equalsSuccess_All() {

        //setting up all the lists and amounts
        ArrayList<VegetarianMeal> vm = new ArrayList<>();
        VegetarianMeal e1 = new VegetarianMeal();
        vm.add(e1);
        ArrayList<LocalProduce> lp = new ArrayList<>();
        LocalProduce e2 = new LocalProduce();
        lp.add(e2);
        ArrayList<SolarPanel> sp = new ArrayList<>();
        SolarPanel e3 = new SolarPanel();
        sp.add(e3);
        ArrayList<LowerTemperature> lt = new ArrayList<>();
        LowerTemperature e4 = new LowerTemperature();
        lt.add(e4);
        ArrayList<PublicTransport> pt = new ArrayList<>();
        PublicTransport e5 = new PublicTransport();
        pt.add(e5);
        ArrayList<TravelByBike> tbb = new ArrayList<>();
        TravelByBike e6 = new TravelByBike();
        tbb.add(e6);
        ArrayList<String> ach = new ArrayList<>();
        String e7 = "Achievement";
        ach.add(e7);
        ArrayList<OverviewElement> ov = new ArrayList<>();
        OverviewElement e8 = new OverviewElement();
        ov.add(e8);
        double total = 20;

        //setting up test1
        Friend test1 = new Friend("andy");
//        test1.setAchievements(ach);
        test1.setLocalProduces(lp);
        test1.setLowerTemperatures(lt);
        test1.setOverview(ov);
        test1.setPublicTransports(pt);
        test1.setSolarPanels(sp);
        test1.setTravelByBikes(tbb);
        test1.setVegetarianMeals(vm);
        test1.setTotalEnergy(total);
        test1.setTotalFood(total);
        test1.setTotalTransport(total);

        //setting up test2 for comparison
        Friend test2 = new Friend("andy");
//        test2.setAchievements(ach);
        test2.setLocalProduces(lp);
        test2.setLowerTemperatures(lt);
        test2.setOverview(ov);
        test2.setPublicTransports(pt);
        test2.setSolarPanels(sp);
        test2.setTravelByBikes(tbb);
        test2.setVegetarianMeals(vm);
        test2.setTotalEnergy(total);
        test2.setTotalFood(total);
        test2.setTotalTransport(total);

        assertEquals(test1, test2);
    }

    @Test
    void equalsFail_differentSize() {

        //setting up all the lists and amounts
        ArrayList<VegetarianMeal> vm = new ArrayList<>();
        ArrayList<VegetarianMeal> vm2 = new ArrayList<>();
        VegetarianMeal e1 = new VegetarianMeal();
        vm.add(e1);
        ArrayList<LocalProduce> lp = new ArrayList<>();
        ArrayList<LocalProduce> lp2 = new ArrayList<>();
        LocalProduce e2 = new LocalProduce();
        lp.add(e2);
        ArrayList<SolarPanel> sp = new ArrayList<>();
        ArrayList<SolarPanel> sp2 = new ArrayList<>();
        SolarPanel e3 = new SolarPanel();
        sp.add(e3);
        ArrayList<LowerTemperature> lt = new ArrayList<>();
        ArrayList<LowerTemperature> lt2 = new ArrayList<>();
        LowerTemperature e4 = new LowerTemperature();
        lt.add(e4);
        ArrayList<PublicTransport> pt = new ArrayList<>();
        ArrayList<PublicTransport> pt2 = new ArrayList<>();
        PublicTransport e5 = new PublicTransport();
        pt.add(e5);
        ArrayList<TravelByBike> tbb = new ArrayList<>();
        ArrayList<TravelByBike> tbb2 = new ArrayList<>();
        TravelByBike e6 = new TravelByBike();
        tbb.add(e6);
        double total = 20;
        double total2 = 30;

        //setting up test1
        Friend test1 = new Friend("andy");
        test1.setLocalProduces(lp);
        test1.setLowerTemperatures(lt);
        test1.setPublicTransports(pt);
        test1.setSolarPanels(sp);
        test1.setTravelByBikes(tbb);
        test1.setVegetarianMeals(vm);
        test1.setTotalEnergy(total);
        test1.setTotalFood(total);
        test1.setTotalTransport(total);

        //setting up test2 for comparison
        Friend test2 = new Friend("andy");
        test2.setLocalProduces(lp2);
        test2.setLowerTemperatures(lt2);
        test2.setPublicTransports(pt2);
        test2.setSolarPanels(sp2);
        test2.setTravelByBikes(tbb2);
        test2.setVegetarianMeals(vm2);
        test2.setTotalEnergy(total2);
        test2.setTotalFood(total2);
        test2.setTotalTransport(total2);

        assertNotEquals(test1, test2);
    }

    @Test
    void equalsFail_differentList() {

        //setting up all the lists and amounts
        ArrayList<VegetarianMeal> vm = new ArrayList<>();
        ArrayList<VegetarianMeal> vm2 = new ArrayList<>();
        VegetarianMeal e1 = new VegetarianMeal();
        VegetarianMeal e12 = new VegetarianMeal();
        vm.add(e1);
        vm2.add(e12);
        ArrayList<LocalProduce> lp = new ArrayList<>();
        ArrayList<LocalProduce> lp2 = new ArrayList<>();
        LocalProduce e2 = new LocalProduce();
        LocalProduce e22 = new LocalProduce();
        lp.add(e2);
        lp2.add(e22);
        ArrayList<SolarPanel> sp = new ArrayList<>();
        ArrayList<SolarPanel> sp2 = new ArrayList<>();
        SolarPanel e3 = new SolarPanel();
        SolarPanel e32 = new SolarPanel();
        sp.add(e3);
        sp2.add(e32);
        ArrayList<LowerTemperature> lt = new ArrayList<>();
        ArrayList<LowerTemperature> lt2 = new ArrayList<>();
        LowerTemperature e4 = new LowerTemperature();
        LowerTemperature e42 = new LowerTemperature();
        lt.add(e4);
        lt2.add(e42);
        ArrayList<PublicTransport> pt = new ArrayList<>();
        ArrayList<PublicTransport> pt2 = new ArrayList<>();
        PublicTransport e5 = new PublicTransport();
        PublicTransport e52 = new PublicTransport();
        pt.add(e5);
        pt2.add(e52);
        ArrayList<TravelByBike> tbb = new ArrayList<>();
        ArrayList<TravelByBike> tbb2 = new ArrayList<>();
        TravelByBike e6 = new TravelByBike();
        TravelByBike e62 = new TravelByBike();
        tbb.add(e6);
        tbb2.add(e62);
        double total = 20;
        double total2 = 30;

        //setting up test1
        Friend test1 = new Friend("andy");
        test1.setLocalProduces(lp);
        test1.setLowerTemperatures(lt);
        test1.setPublicTransports(pt);
        test1.setSolarPanels(sp);
        test1.setTravelByBikes(tbb);
        test1.setVegetarianMeals(vm);
        test1.setTotalEnergy(total);
        test1.setTotalFood(total);
        test1.setTotalTransport(total);

        //setting up test2 for comparison
        Friend test2 = new Friend("andy");
        test2.setLocalProduces(lp2);
        test2.setLowerTemperatures(lt2);
        test2.setPublicTransports(pt2);
        test2.setSolarPanels(sp2);
        test2.setTravelByBikes(tbb2);
        test2.setVegetarianMeals(vm2);
        test2.setTotalEnergy(total2);
        test2.setTotalFood(total2);
        test2.setTotalTransport(total2);

        assertNotEquals(test1, test2);
    }

    @Test
    void calculateTotalAmounts() {
        ArrayList<VegetarianMeal> vm = new ArrayList<>();
        VegetarianMeal e1 = new VegetarianMeal();
        vm.add(e1);
        ArrayList<LocalProduce> lp = new ArrayList<>();
        LocalProduce e2 = new LocalProduce();
        lp.add(e2);
        ArrayList<SolarPanel> sp = new ArrayList<>();
        SolarPanel e3 = new SolarPanel();
        sp.add(e3);
        ArrayList<LowerTemperature> lt = new ArrayList<>();
        LowerTemperature e4 = new LowerTemperature();
        lt.add(e4);
        ArrayList<PublicTransport> pt = new ArrayList<>();
        PublicTransport e5 = new PublicTransport();
        pt.add(e5);
        ArrayList<TravelByBike> tbb = new ArrayList<>();
        TravelByBike e6 = new TravelByBike();
        tbb.add(e6);

        test.setLocalProduces(lp);
        test.setLowerTemperatures(lt);
        test.setPublicTransports(pt);
        test.setSolarPanels(sp);
        test.setTravelByBikes(tbb);
        test.setVegetarianMeals(vm);

        ArrayList<OverviewElement> ov = new ArrayList<>();
        OverviewElement e8 = new OverviewElement();
        ov.add(e8);
        test.setOverview(ov);

        test.calculateTotalAmounts();
        assertEquals(0.0, test.getTotal());
    }
}