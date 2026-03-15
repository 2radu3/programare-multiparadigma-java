package practice;

class AnnalynsInfiltration {
    public static void main(String[] args) {
        boolean knightIsAwake = false;
        boolean archerIsAwake = false;
        boolean prisonerIsAwake = true;
        boolean petDogIsPresent = false;
        AnnalynsInfiltration.canFastAttack(knightIsAwake);
        AnnalynsInfiltration.canSpy(knightIsAwake, archerIsAwake, prisonerIsAwake);
        AnnalynsInfiltration.canSignalPrisoner(archerIsAwake, prisonerIsAwake);
        AnnalynsInfiltration.canFreePrisoner(knightIsAwake, archerIsAwake, prisonerIsAwake, petDogIsPresent);
    }
    public static boolean canFastAttack(boolean knightIsAwake) {
        return !knightIsAwake;
    }

    public static boolean canSpy(boolean knightIsAwake, boolean archerIsAwake, boolean prisonerIsAwake) {
        return knightIsAwake || archerIsAwake || prisonerIsAwake;
    }

    public static boolean canSignalPrisoner(boolean archerIsAwake, boolean prisonerIsAwake) {
        return prisonerIsAwake && !archerIsAwake;
    }

    public static boolean canFreePrisoner(boolean knightIsAwake, boolean archerIsAwake, boolean prisonerIsAwake, boolean petDogIsPresent) {
        if (petDogIsPresent) {
            return !archerIsAwake;
        } else {
            return prisonerIsAwake && !knightIsAwake && !archerIsAwake;
        }
    }
}
