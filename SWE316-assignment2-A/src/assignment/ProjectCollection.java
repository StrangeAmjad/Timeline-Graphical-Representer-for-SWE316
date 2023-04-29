package assignment;
import java.util.Date;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ProjectCollection {
	private static Project[] projects;
	private static HashMap<String, ArrayList<Stage>> stages;
	public static Project[] start(String path1, String path2,String path3) throws IOException {
		projects = readProjects(path1);
		stages = readStages(path2,path3);
		mergeProjectStages();
		return projects;
	
}

	@SuppressWarnings("deprecation")
	private static Project[] readProjects(String Path) throws IOException {
		FileInputStream file = new FileInputStream(new File(Path));
		try (Workbook workbook = new HSSFWorkbook(file)) {
			Sheet sheet = workbook.getSheetAt(0);
			int j = 0;
			int i = 0;
			ArrayList<Project> projects = new ArrayList<Project>();
			for (Row row : sheet) {
				if (i == 0) {
					i++;
					continue;
				}
				;
				j = 0;
				String projectID = null;
				int currentStage = 0;
				Date startDate = null;
				Date endDate = null;
				int customerID = 0;
				String currency = null;
				Date createdOn = null;
				Date changedOn = null;
				String nodeID = null;
				for (Cell cell : row) {

					switch (cell.getCellType()) {
					case 1:
						if (j == 0) {
							nodeID = cell.getStringCellValue();
						}
						if (j == 1) {
							projectID = cell.getStringCellValue();
						}
						if (j == 6) {
							currency = cell.getStringCellValue();
						}
						if (j == 7) {
							String tempDate = cell.getStringCellValue();
							String[] dNT = tempDate.split(" ");
							String day, month, year, hour, min, sec;
							day = dNT[0].charAt(0) + "" + dNT[0].charAt(1);
							month = dNT[0].charAt(3) + "" + dNT[0].charAt(4);
							year = dNT[0].charAt(6) + "" + dNT[0].charAt(7) + "" + dNT[0].charAt(8) + dNT[0].charAt(9);
							hour = dNT[1].charAt(0) + "" + dNT[1].charAt(1);
							min = dNT[1].charAt(3) + "" + dNT[1].charAt(4);
							sec = dNT[1].charAt(6) + "" + dNT[1].charAt(7);
							createdOn = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1,
									Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(min),
									Integer.parseInt(sec));
						}
						if (j == 8) {
							String tempDate = cell.getStringCellValue();
							String[] dNT = tempDate.split(" ");
							String day, month, year, hour, min, sec;
							day = dNT[0].charAt(0) + "" + dNT[0].charAt(1);
							month = dNT[0].charAt(3) + "" + dNT[0].charAt(4);
							year = dNT[0].charAt(6) + "" + dNT[0].charAt(7) + "" + dNT[0].charAt(8) + dNT[0].charAt(9);
							hour = dNT[1].charAt(0) + "" + dNT[1].charAt(1);
							min = dNT[1].charAt(3) + "" + dNT[1].charAt(4);
							sec = dNT[1].charAt(6) + "" + dNT[1].charAt(7);
							changedOn = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month),
									Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(min),
									Integer.parseInt(sec));
						}

						break;
					case 0:
						if (DateUtil.isCellDateFormatted(cell)) {
							Date D = cell.getDateCellValue();
							if (j == 3) {
								startDate = D;
							}
							if (j == 4) {
								endDate = D;
							}
						}

						else {
							if (j == 2) {
								currentStage = (int) cell.getNumericCellValue();
							}
							if (j == 5) {
								customerID = (int) cell.getNumericCellValue();
							}
						}
						break;

					default:
						;
					}
					j++;
				}
				i++;
				Project project = new Project(nodeID, projectID, currentStage);
				projects.add(project);
			}
			Project[] projectsReturn = new Project[projects.size()];
			projects.toArray(projectsReturn);
			return projectsReturn;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		finally {
			file.close();
		}
		return new Project[0];
	}

	@SuppressWarnings("deprecation")
	private static HashMap<String,ArrayList<Stage>> readStages(String Path1, String Path2) throws IOException {
		FileInputStream file1 = new FileInputStream(new File(Path1));
		FileInputStream file2 = new FileInputStream(new File(Path2));
		HashMap<String,ArrayList<Stage>> stageMap= new HashMap<String, ArrayList<Stage>>();
		try (Workbook workbook = new HSSFWorkbook(file1); Workbook workbook1 = new HSSFWorkbook(file2)) {
			Sheet sheet = workbook.getSheetAt(0);
			Sheet sheet1 = workbook1.getSheetAt(0);

			int j = 0;
			int i = 0;
			ArrayList<Stage> stages = new ArrayList<Stage>();

			for (Row row : sheet) {
				Row row1 = sheet1.getRow(i);
				if (i == 0) {
					i++;
					continue;
				}
				;
				j = 0;
				int prevStage = 0;
				int stageNum = 0;
				String indicator = null;
				String obVal = null;
				int docNum = 0;
				int flag = 0;
				String fieldName = null;
				Date date = null;
				String lang = null;
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case 1:
						if (j == 0) {
							obVal = cell.getStringCellValue();
						}
						if (j == 2) {
							fieldName = cell.getStringCellValue();
						}
						if (j == 3) {
							indicator = cell.getStringCellValue();
						}
						break;
					case 0:
						if (j == 1) {
							docNum = (int) cell.getNumericCellValue();
						}
						if (j == 4) {
							flag = (int) cell.getNumericCellValue();
						}
						if (j == 5) {
							stageNum = (int) cell.getNumericCellValue();
						}
						if (j == 6) {
							prevStage = (int) cell.getNumericCellValue();
						}

						break;

					default:
						;
					}
					j++;
				}
				 j = 0;

				for (Cell cell : row1) {
					switch (cell.getCellType()) {
					case 1:
					
						if (j == 4) {
							lang = cell.getStringCellValue();
						}
						break;
					case 0:
						if (j == 2) {
							if (DateUtil.isCellDateFormatted(cell)) {
								date = cell.getDateCellValue();
								
							}
						}
						if (j == 3) {
							if (DateUtil.isCellDateFormatted(cell)) {
								date.setHours(cell.getDateCellValue().getHours());
								date.setMinutes(cell.getDateCellValue().getMinutes());
								date.setSeconds(cell.getDateCellValue().getSeconds());
							}

							
						}

						break;

					default:
						;
					}
					j++;
				}
				i++;
				Stage stage = new Stage(obVal,stageNum,prevStage,date);
				if(!stageMap.containsKey(obVal)) {
				stageMap.put(obVal,new ArrayList<Stage>());
				}
				stageMap.get(obVal).add(stage);
				prevStage = 0;
				stageNum = 0;
				indicator = null;
				obVal = null;
				docNum = 0;
				flag = 0;
				fieldName = null;
				date = null;
				lang = null;
				stages.add(stage);
			}
			return stageMap;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		finally {
			file1.close();
			file2.close();
		}
			
		
			
		return stageMap;
	}
	private static void mergeProjectStages() {
		for(Project p : projects) {
			Collections.sort(stages.get(p.getNodeID()));
			p.setStages(stages.get(p.getNodeID()));
		}
	}
}
