package project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.sf.javaml.core.*;
import java.util.*;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.distance.JaccardIndexDistance;
import net.sf.javaml.distance.JaccardIndexSimilarity;
import net.sf.javaml.distance.NormalizedEuclideanDistance;
import static net.sf.javaml.tools.DatasetTools.createInstanceFromAttribute;
import static net.sf.javaml.tools.DatasetTools.createInstanceFromClass;
/**
 *
 * @author SATYAM
 */
public class mlbean {
    
        
    public String train(int USER_ID,int PRODUCT_ID,int RATING)
    {
        String folderPath = "C:/Users/SATYAM/Desktop/ML";
        Path path = Paths.get(folderPath, "sample_als.txt"); //or any text file eg.: txt, bat, etc
        //Charset charset = Charset.forName("UTF-8");

        String line;
        Dataset user=new DefaultDataset();
        Dataset product=new DefaultDataset();
        Instance inst;
        Map userID=new HashMap();
        Map productID=new HashMap();
        double i=0,j=0;
        try (BufferedReader reader = Files.newBufferedReader(path)) 
        {
          
            while ((line = reader.readLine()) != null ) 
            {
    //separate all csv fields into string array
                String[] str = line.split("::"); 
                
                if(!userID.containsKey(str[0]))
                { userID.put(str[0],i++); }
                if(!productID.containsKey(str[1]))
                { productID.put(str[1],j++); }
                
                double tmp[]=new double[2];
                tmp[0]=(double)productID.get(str[1]);
                tmp[1]=Double.parseDouble(str[2]);
                
                inst=new DenseInstance(tmp,Integer.parseInt(str[0]));
                user.add(inst);
                
                tmp[0]=(double)userID.get(str[0]);
                inst=new DenseInstance(tmp,Integer.parseInt(str[1]));
                
                product.add(inst);
                
                
            }
        }
        catch (IOException e) {
            System.err.println(e);

         }
        
       //System.out.println("Users data {ProductRated,Rating}UserID:\n"+user);
        //System.out.println("Product Table {RatingUser,Rating}ProductID:\n"+product);
    SortedSet user_set=user.classes();
    SortedSet product_set=product.classes();
    
    //System.out.println("Total number of users : "+user_set.size());
    //System.out.println("Total number of products :"+product_set.size());
    //System.out.println("Total Ratings : "+user.size());
    
    //System.out.println("USERS :"+user_set);
    //System.out.println("PRODUCTS :"+product_set);        

        
//System.out.println("EVALUATE USER CLASSIFICATION USING K-Means");
/*Classifier knn = new KNearestNeighbors(5000);
knn.buildClassifier(user);
CrossValidation cv = new CrossValidation(knn);
/* Perform cross-validation on the data set 
Map<Object, PerformanceMeasure> p = cv.crossValidation(user);
Double RMS=0.0;
Double average=0.0;

for(Object o:p.keySet())
{  
    System.out.println("User ID : "+o+", Accuracy: "+p.get(o).getAccuracy()+" Error Rate:"+p.get(o).getErrorRate());
    average+=p.get(o).getErrorRate();
    //RMS+=Math.pow(p.get(o).getErrorRate(),2);
}
int n=user_set.size();
average=average/n;
System.out.println("Average :"+average);
for(Object o:p.keySet())
{  
    //System.out.println("User ID : "+o+", Accuracy: "+p.get(o).getAccuracy()+" Error Rate:"+p.get(o).getErrorRate());
    RMS+=Math.pow((average-p.get(o).getErrorRate()),2);
    //RMS+=Math.pow(p.get(o).getErrorRate(),2);
}
RMS=Math.sqrt(RMS/n);


System.out.println("Root Mean Squared Error :"+RMS);

System.out.println("EVALUATE PRODUCT CLASSFICIATION USING K-Means");
Classifier knn2=new KNearestNeighbors(5000);
knn2.buildClassifier(product);
CrossValidation cv2 = new CrossValidation(knn2);
/* Perform cross-validation on the data set 
p = cv2.crossValidation(product);
average=0.0;
RMS=0.0;
for(Object o:p.keySet())
{  System.out.println("Product ID : "+o+", Accuracy: "+p.get(o).getAccuracy()+" Error Rate:"+p.get(o).getErrorRate());
   average+=p.get(o).getErrorRate();       
}  
n=product_set.size();
average=average/n;
for(Object o:p.keySet())
{  //System.out.println("Product ID : "+o+", Accuracy: "+p.get(o).getAccuracy()+" Error Rate:"+p.get(o).getErrorRate());
   RMS+=Math.pow((average-p.get(o).getErrorRate()),2);      
}     
RMS=Math.sqrt(RMS/n);

    System.out.println("Average : "+average);
    System.out.println("Root Mean Squared Error :"+RMS);
    
   */ 
    
    
    
        Clusterer km3=new KMeans(4);
/* We cluster the data */
        Dataset[] clusters = km3.cluster(user);
/* Create a measure for the cluster quality */
        ClusterEvaluation sse= new SumOfSquaredErrors();
/* Measure the quality of the clustering */
        double score=sse.score(clusters);
        
       // System.out.println("\nCluster1 :"+clusters[0]);
       // System.out.println("\nCluster2 :"+clusters[1]);
       // System.out.println("\nCluster3 :"+clusters[2]);
        //System.out.println("\nCluster4 :"+clusters[3]);
        //System.out.println("\nScore :"+score);
        /*Classifier kmm4=new KNearestNeighbors(5000);
        kmm4.buildClassifier(clusters[0]);
        System.out.println(user.instance((int)kmm4.classify(temp)));
        Classifier kmm5=new KNearestNeighbors(5000);
        kmm5.buildClassifier(clusters[1]);
        System.out.println(user.instance((int)kmm5.classify(temp)));
        Classifier kmm6=new KNearestNeighbors(5000);
        kmm6.buildClassifier(clusters[2]);
        System.out.println(user.instance((int)kmm6.classify(temp)));
        */
    
    /*InputStreamReader x=new InputStreamReader(System.in);
    BufferedReader y=new BufferedReader(x);
    System.out.print("\nEnter USER ID :");
    int user_id=Integer.parseInt(y.readLine());
    System.out.print("\nEnter PRODUCT ID :");
    double product_id=Double.parseDouble(y.readLine());
    System.out.print("\nEnter the Rating :");
    double rating=Double.parseDouble(y.readLine());
    System.out.println();
    */
    
   
    double[] tp={PRODUCT_ID,RATING};
    DistanceMeasure dm=new JaccardIndexSimilarity();
    int tmp;
    tmp=user.classIndex((USER_ID));
    //{ 
         Instance temp=(Instance)user.instance(tmp);
    //     Set<Instance> st=user.kNearest(5000, temp, dm);
    //     System.out.println(temp+"K Nearest :"+st);
    //}
    temp=new DenseInstance(tp,USER_ID);
    Set<Instance> st=user.kNearest(5000, temp, dm);
    
    return (temp+" : K nearest: "+st);
    
    
    
}
    
}
