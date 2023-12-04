package com.test;

import java.util.*;

public class Main {
    static int geneSize = 5; // 유전자의 개수 (장소의 개수)
    static int popSize = 100; // 개체군의 크기
    static int generationSize = 100; // 세대 수
    static int[][] places = {{3, 50}, {2, 30}, {1, 20}, {2, 40}, {3, 60}}; // 각 장소의 정보 (방문 시간, 만족도)
    static int totalTime = 6; // 여행 일정의 총 시간

    public static void main(String[] args) {
        int[][] population = new int[popSize][geneSize]; // 개체군
        Random rand = new Random();

        // 초기 개체군 생성
        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < geneSize; j++) {
                population[i][j] = rand.nextInt(2);
            }
        }

        int[] fitnesses = new int[0];
        for (int gen = 0; gen < generationSize; gen++) {
            fitnesses = new int[popSize];

            // 각 개체의 적합도 계산
            for (int i = 0; i < popSize; i++) {
                int time = 0;
                int satisfaction = 0;
                for (int j = 0; j < geneSize; j++) {
                    if (population[i][j] == 1) {
                        time += places[j][0];
                        satisfaction += places[j][1];
                    }
                }
                if (time > totalTime) { // 총 시간을 초과하면 적합도는 0
                    fitnesses[i] = 0;
                } else {
                    fitnesses[i] = satisfaction;
                }
            }

            int[][] newPopulation = new int[popSize][geneSize]; // 새 개체군

            // 새 개체군 생성
            for (int i = 0; i < popSize; i++) {
                // 선택
                int[] parent1 = select(population, fitnesses);
                int[] parent2 = select(population, fitnesses);
                // 교차
                int crossoverPoint = rand.nextInt(geneSize);
                int[] offspring = new int[geneSize];
                System.arraycopy(parent1, 0, offspring, 0, crossoverPoint);
                System.arraycopy(parent2, crossoverPoint, offspring, crossoverPoint, geneSize - crossoverPoint);
                // 돌연변이
                if (rand.nextDouble() < 0.1) {
                    int mutationPoint = rand.nextInt(geneSize);
                    offspring[mutationPoint] = 1 - offspring[mutationPoint];
                }
                newPopulation[i] = offspring;
            }

            population = newPopulation;
        }

        // 최적 해 출력
        int[] bestIndividual = select(population, fitnesses);
        System.out.println(Arrays.toString(bestIndividual));
    }

    // 룰렛 휠 선택
    static int[] select(int[][] population, int[] fitnesses) {
        int fitnessSum = Arrays.stream(fitnesses).sum();
        int value = new Random().nextInt(fitnessSum);
        for (int i = 0; i < popSize; i++) {
            value -= fitnesses[i];
            if (value < 0) {
                return population[i];
            }
        }
        return population[0];
    }
}
