# SENG 272 - Software System Quality Evaluation Tool (ISO/IEC 25010)

[cite_start]This project is a measurement tool developed to evaluate the quality of software systems based on the **ISO/IEC 25010** (SQuaRE) standard and concrete metrics from **ISO/IEC 25023**. [cite_start]It utilizes Object-Oriented Programming (OOP) principles and the Java Collections Framework (ArrayList, HashMap) to provide a structured quality report[cite: 2, 5].

## 1. Project Objective
The objective of this application is to:
* [cite_start]Model eight primary quality characteristics defined by ISO/IEC 25010[cite: 13].
* [cite_start]Implement measurable software quality metrics as defined in ISO/IEC 25023[cite: 11, 26].
* [cite_start]Normalize various metric values into a standardized 1-5 score[cite: 36, 44].
* [cite_start]Identify the "weakest dimension" of a system to guide improvement efforts[cite: 68, 69].

## 2. ISO/IEC 25023 Metric Reference Table
[cite_start]The following metrics are used to evaluate the characteristics of the software systems:

| Characteristic | Metric Name | Direction | Unit | Formula Summary |
| :--- | :--- | :--- | :--- | :--- |
| **Functional Suitability** | Functional Completeness Ratio | Higher | % | Implemented / Planned functions * 100 |
| **Functional Suitability** | Functional Correctness Ratio | Higher | % | Correct-output tests / Total tests * 100 |
| **Reliability** | Availability Ratio | Higher | % | Uptime / (Uptime + Downtime) * 100 |
| **Reliability** | Defect Density | Lower | defect/KLOC | Defects found / 1000 LOC |
| **Performance Efficiency** | Response Time | Lower | ms | Average end-to-end response time |
| **Performance Efficiency** | CPU Utilisation Ratio | Lower | % | CPU used / Total CPU * 100 |
| **Maintainability** | Test Coverage Ratio | Higher | % | Tested LOC / Total LOC * 100 |
| **Maintainability** | Cyclomatic Complexity (avg) | Lower | score | Average cyclomatic complexity per module |

## 3. Evaluation Rules
### Score Normalization
All measured values are converted to a normalized score between **1 and 5**[cite: 36]:
* [cite_start]**Higher is better:** $$1 + \frac{measuredValue - minValue}{maxValue - minValue} \times 4$$ [cite: 38]
* [cite_start]**Lower is better:** $$5 - \frac{measuredValue - minValue}{maxValue - minValue} \times 4$$ [cite: 40]
* [cite_start]Scores are clamped between 1 and 5 and rounded to the nearest **0.5**[cite: 42, 43].

### Quality Labels
[cite_start]Final scores are classified according to the following ranges[cite: 58]:
| Score Range | Quality Label |
| :--- | :--- |
| 4.5 - 5.0 | Excellent Quality |
| 3.5 - 4.4 | Good Quality |
| 2.5 - 3.4 | Needs Improvement |
| 1.0 - 2.4 | Poor Quality |

## 4. Project Structure
[cite_start]The project follows the required structure for modularity and encapsulation[cite: 177, 178]:
- [cite_start]**`Criterion.java`**: Represents ISO/IEC 25023 metrics and score calculation[cite: 180].
- [cite_start]**`QualityDimension.java`**: Models ISO/IEC 25010 characteristics[cite: 181].
- [cite_start]**`SWSystem.java`**: Represents the software product and generates reports[cite: 182].
- [cite_start]**`SWSystemData.java`**: Utility class for data categorization using HashMap[cite: 183].
- [cite_start]**`Main.java`**: Entry point for system evaluation and console output[cite: 184].

## 5. How to Run
1. Open the project in an IDE (e.g., IntelliJ IDEA or Eclipse).
2. Run the `Main.java` file.
3. [cite_start]The system will load the "ShopSphere" dataset and print a detailed ISO/IEC 25010 report to the console[cite: 152, 153].