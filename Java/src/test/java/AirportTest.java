import java.util.Arrays;
import java.util.List;

import models.ClassificationLevel;
import models.ExperimentalType;
import models.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;
import planes.ExperimentalPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

public class AirportTest {
    private static List<Plane> Planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalType.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalType.VTOL, ClassificationLevel.TOP_SECRET)
    );

    private static PassengerPlane maxPassengerCapacityPlane = new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);

    @Test
    public void isTransportMilitaryPlanes() {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> transportMilitaryPlanes = airport.getTransportMilitaryPlanes();
        boolean isTransportMilitaryPlane = false;
        for (MilitaryPlane militaryPlane : transportMilitaryPlanes) {
            if (militaryPlane.getType() == MilitaryType.TRANSPORT) {
                isTransportMilitaryPlane = true;
                break;
            }
        }
        Assert.assertEquals(isTransportMilitaryPlane, true);
    }

    @Test
    public void getMaxPassengerCapacityPlane() {
        System.out.println("getMaxPassengerCapacityPlane started!");
        Airport airport = new Airport(planes);
        PassengerPlane expectedPlane = airport.getMaxPassengerCapacityPlane();
        Assert.assertTrue(expectedPlane.equals(maxPassengerCapacityPlane));
        System.out.println("getMaxPassengerCapacityPlane finished!");
    }

    @Test
    public void isNextPlaneLoadCapacityHigher() {
        Airport airport = new Airport(planes);
        airport.sortByMaxLoadCapacity();
        List<? extends Plane> sortedPlanes = airport.getPlanes();

        boolean isNextPlaneLoadCapacityHigher = false;
        for (int i = 0; i < sortedPlanes.size() - 1; i++) {
            Plane currentPlane = sortedPlanes.get(i);
            Plane nextPlane = sortedPlanes.get(i + 1);
            if (currentPlane.getMaxLoadCapacity() < nextPlane.getMaxLoadCapacity()) {
                isNextPlaneLoadCapacityHigher = true;
                break;
            }
        }
        Assert.assertTrue(isNextPlaneLoadCapacityHigher);
    }

    @Test
    public void isBomberInMilitaryPlanes() {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> militaryPlanes = airport.getMilitaryPlanes();
        boolean isBomberPlane = false;
        for (MilitaryPlane militaryPlane : militaryPlanes) {
            if (militaryPlane.getType() == MilitaryType.BOMBER) {
                isBomberPlane = true;
            }
        }
        Assert.assertTrue(isBomberPlane);
    }

    @Test
    public void isExperimentalUnclassifiedPlanes(){
        Airport airport = new Airport(planes);
        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        boolean isUnclassifiedPlanes = false;
        for(ExperimentalPlane experimentalPlane : experimentalPlanes){
            if(experimentalPlane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED){
                isUnclassifiedPlanes = true;
                break;
            }
        }
        Assert.assertFalse(isUnclassifiedPlanes);
    }
}
