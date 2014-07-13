<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="UTF-8" indent="no" />
<xsl:key name="refid" match="node" use="@ID" />

    <xsl:template match="/">
		<html>&#xA;
            <head>&#xA;
                <title><xsl:text>Тестирование проекта "</xsl:text><xsl:value-of select="map/node/@TEXT" /></title>&#xA;
                <style>
                body{
                font-size:10pt;
                color:rgb(45,45,45);
                backgound-color:rgb(255,255,255);
                font-family:sans-serif;
                }
                p.info{
                font-size:8pt;
                text-align:right;
                color:rgb(127,127,127);
                }
                </style>
            </head>
            <body>
          
				<h1>
					<xsl:text>Тестирование проекта "</xsl:text>
					<xsl:value-of select="map/node/@TEXT" />
					<xsl:text>"</xsl:text>
				</h1>
				<ul>
					<xsl:apply-templates select="//node" mode="main"/>
				</ul>
				<xsl:text><hr /></xsl:text>

		<!--		<h2>
					<xsl:text>&#xA;&#xA;Проверено:</xsl:text>
				</h2>
				<ul>
				<xsl:apply-templates select="//node" mode="passed"/>
				</ul>
				-->
				<h2>
					<xsl:text>&#xA;Ошибки:</xsl:text>
				</h2>
				<ul>
				<xsl:apply-templates select="//node" mode="failed"/>
				</ul>
				<h2>
					<xsl:text>&#xA;Не проверено:</xsl:text>
				</h2>
				<ul>
					<xsl:apply-templates select="//node" mode="notyet"/>
				</ul>
		</body>&#xA;
        </html>&#xA;
    </xsl:template>

<xsl:template match="node" mode="main">
	<xsl:text>&#xA;</xsl:text>
    <xsl:if test="attribute[@NAME='chapter']">
		<h2>
			<xsl:value-of select="@TEXT"/>
		</h2>
	</xsl:if>
    <xsl:if test="attribute[@NAME='suite']">
		<h3>
			<xsl:number level="multiple" count="node" format="1"/>
			<xsl:text> </xsl:text>		
			<xsl:value-of select="@TEXT"/>
		</h3>
	</xsl:if>
    <xsl:if test="attribute[@NAME='test' and @VALUE='failed']">
		<li>
		<font color='#DF1818'>
		<xsl:value-of select="@TEXT"/>
		<xsl:text> (-)</xsl:text>
		<!--<xsl:value-of select="attribute[@NAME='test']/@VALUE"/>-->
		</font>
		</li>
	</xsl:if>
	    <xsl:if test="attribute[@NAME='test' and @VALUE='passed']">
		<li>
		<font color='#198100'>
		<xsl:value-of select="@TEXT"/>
		<xsl:text> (+)</xsl:text>
		<!--<xsl:value-of select="attribute[@NAME='test']/@VALUE"/>-->
		</font>
		</li>
	</xsl:if>
	    <xsl:if test="attribute[@NAME='test' and @VALUE='']">
		<li>
		<font color='#002ECE'>                                                                                                            
		<xsl:value-of select="@TEXT"/>
		<xsl:text> </xsl:text>
		<!--<xsl:value-of select="attribute[@NAME='test']/@VALUE"/>-->
		</font>
		</li>
	</xsl:if>
	<i>
		<xsl:value-of select="richcontent"/>
	</i>
</xsl:template>

<xsl:template match="//node" mode="failed">
    <xsl:if test="attribute[@NAME='test' and @VALUE='failed']">
		<li>
		<xsl:value-of select="../@TEXT"/>
		<xsl:text>.</xsl:text>
		<xsl:value-of select="@TEXT"/>
		</li>
	</xsl:if>
</xsl:template>

<xsl:template match="//node" mode="passed">
    <xsl:if test="attribute[@NAME='test' and @VALUE='passed']">
		<li>
		<xsl:value-of select="../@TEXT"/>
		<xsl:text>.</xsl:text>
		<xsl:value-of select="@TEXT"/>
		</li>
	</xsl:if>
</xsl:template>

<xsl:template match="//node" mode="notyet">
    <xsl:if test="attribute[@NAME='test' and @VALUE='']">
		<li>
		<xsl:value-of select="../@TEXT"/>
		<xsl:text>.</xsl:text>
		<xsl:value-of select="@TEXT"/>
		</li>
	</xsl:if>
</xsl:template>
</xsl:stylesheet>
