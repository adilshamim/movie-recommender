package client;
import database.*;
import java.util.*;
/**
 * This is the final class to recommend movies on web page
 * this class has Fourth rating object to get similar ratings
 * @author Adil Shamim 
 * @version september,2020
 */

public class RecommendationRunner implements Recommender {
    private FourthRating fr;
    private ArrayList<String> ans;

    public RecommendationRunner()
    {
        fr=new FourthRating();
        ans = new ArrayList<String>();
    }

    public ArrayList<String> getItemsToRate ()
    {
        ArrayList<Integer> in=new ArrayList<Integer>();
        Random ran = new Random();
        //ran.setSeed(652);
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        //parametr is rater_id(reference taste),no. of top raters similar to rater_id,minimal raters
          ArrayList<Rating> rating=fr.getSimilarRatings("71",20,5);
        int size=rating.size()-1;
        for(int k=0;k<15;k++)//only 15 movies to add
        {

            int rand=ran.nextInt(size);
            if(!in.contains(rand))
            {
                in.add(rand);
                ans.add(rating.get(rand).getItem());
                System.out.println(MovieDatabase.getTitle(rating.get(rand).getItem()));
            }
        }  
        return ans;   
    }

    public void printRecommendationsFor (String webRaterID)
    {
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        ArrayList<Rating> rating=fr.getSimilarRatings(webRaterID,20,5);
        Collections.sort(rating,Collections.reverseOrder());//only top rated movies to diplay
        System.out.print("<html><head><title>Recommender</title>");
        System.out.print("<style>body{background: rgb(105,121,198);");
        System.out.print("background: radial-gradient(circle, rgba(105,121,198,1) 0%, rgba(228,63,255,0.5130427170868348) 100%);}");
        System.out.print("table{border-collapse: collapse;");
        System.out.print("margin-left:auto;margin-right:auto;");
        System.out.print("font-size: 0.9em;font-family:sans-serif;");
        System.out.print("min-width:800px;height:200px;");
        System.out.print("box-shadows:10px 10px 20px 0px rgba(0,0,0,0.75);}");
        System.out.print("th:first-child{border-radius: 10px 0 0 0;}");
        System.out.print("th:last-child{border-radius: 0 10px 0 0;}");
        System.out.print("th:only-child{border-radius: 10px 10px 0 0;}");
        System.out.print("table thead tr{");
        System.out.print("background-color: #3249b5;");
        System.out.print("color:#ffffff;text-align: left;}");
        System.out.print("table th,table{");
        System.out.print("padding: 12px 15px;}");
        System.out.print("table table tr{");
        System.out.print("border-bottom: 1px solid #dddddd;}");
        System.out.print("table tbody tr:nth-of-type(even){");
        System.out.print("background-color: #f3f3f3;}");
        System.out.print("table tbody tr:last-of-type{");
        System.out.print("border-bottom: 2px solid #3249b5;}");
        System.out.print("table tbody tr.active-row{");
        System.out.print("font-weight: bold;color:#3249b5;}");
        System.out.print("table tbody tr.active-row1{");
        System.out.print("font-weight: bold;background-color:#C0C0C0;}");
        System.out.print("h1{");
        System.out.print("padding: 60px;text-align: center;");
        System.out.print("text-shadow: 2px 2px 2px #000000;");
        System.out.print("font-family: 'Pacifico', cursive;");
        System.out.print("color:#006400;font-size: 30px;}");
        System.out.print("</style></head><body>");
        System.out.print("<h1>Personalised Movie Recommender</h1>");
        System.out.print("<table><thead><tr>");
        System.out.print("<th>Rank</th><th>Movie</th>");
        System.out.print("<th>Genre</th><th>Running Time(mins)</th>");
        System.out.print("</tr></thead><tbody>");
        int k=0,m=0;
       
            for( k=0;k<rating.size();k++)
            {
                String id=rating.get(k).getItem();
                String movie=MovieDatabase.getTitle(id);
                int year=MovieDatabase.getYear(id);
                String genre=MovieDatabase.getGenres(id).trim();
                int mins=MovieDatabase.getMinutes(id);
                if(!ans.contains(id))//if user had watch this dont include
                {
                    if(k%2!=0)
                    {
                        m++;
                        System.out.println("<tr class=\"active-row1\">");
                        System.out.println("<td>"+m+"</td>");
                        System.out.println("<td>"+movie+"("+year+")"+"</td>");
                        System.out.println("<td>"+genre+"</td>");
                        System.out.println("<td>"+mins+"</td>");
                        
                        //System.out.println(String.format("%.2f",rating.get(k).getValue()));
                    }

                    else if(k%2==0)
                    {
                        m++;
                        System.out.println("<tr class=\"active-row\">");
                        System.out.println("<td>"+m+"</td>");
                        System.out.println("<td>"+movie+"("+year+")"+"</td>");
                        System.out.println("<td>"+genre+"</td>");
                        System.out.println("<td>"+mins+"</td>");
                        
                        //System.out.println(String.format("%.2f",rating.get(k).getValue()));

                    }
                    
                   
                }
                
                if(m==10)
                    {
                    break;
                    }
            } 
        
         if(k==0)
            {
                System.out.println("<h2 style=\"color:red\">OOPs no movies found to recommend based on your taste\nTry again... <h2>");

            }
        
        System.out.print("</tbody></table>");
        System.out.print("</body></html>");

    }
}
